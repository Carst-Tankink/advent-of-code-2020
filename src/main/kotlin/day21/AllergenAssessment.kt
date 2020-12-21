package day21

import util.Solution

data class Food(val ingredients: List<String>, val allergens: Set<String>)
class AllergenAssessment(fileName: String) : Solution<Food, Long>(fileName) {
    override fun parse(line: String): Food {
        val pattern = """(.*) \(contains (.*)\)""".toRegex()
        val groups = pattern.matchEntire(line)?.groupValues ?: error("Unmatched line $line")
        return Food(groups[1].split(" "), groups[2].split(", ").toSet())
    }

    override fun List<Food>.solve1(): Long {
        val allergens = identifyAllergens()

        val allIngredients = this.flatMap { it.ingredients }
        val safeIngredients: Set<String> = allIngredients.toSet() - allergens.keys


        return safeIngredients.map { safe -> allIngredients.count { it == safe } }.sum().toLong()
    }

    private fun List<Food>.identifyAllergens(): Map<String, String> {
        flatMap { f -> f.ingredients.map { it to f } }
            .groupBy { it.first }
            .mapValues { (_, value) -> value.map { it.second }.toSet() }

        val allergenList: Map<String, Set<Food>> =
            flatMap { f -> f.allergens.map { it to f } }
                .groupBy { it.first }
                .mapValues { (_, value) -> value.map { it.second }.toSet() }


        val uniqueAllergens: Map<String, Set<String>> = allergenList.mapValues { (_, foods) ->
            val ingredientsPerFood = foods.map { it.ingredients.toSet() }
            ingredientsPerFood.drop(1).fold(ingredientsPerFood.first()) { acc, food -> acc.intersect(food) }
        }

        tailrec fun findAllergens(acc: Map<String, String>, candidates: Map<String, Set<String>>): Map<String, String> {
            return if (candidates.isEmpty()) acc else {
                val identified =
                    candidates.entries.find { it.value.size == 1 } ?: error("No allergen with one ingredient")
                val ingredient = identified.value.first()
                val newCandidates = candidates
                    .mapValues { (_, value) -> value - ingredient }
                    .filterValues { it.isNotEmpty() }

                findAllergens(acc + (identified.value.first() to identified.key), newCandidates)
            }
        }

        return findAllergens(emptyMap(), uniqueAllergens)
    }


    override fun List<Food>.solve2(): Long {
        val canonicalDangerous = this.identifyAllergens()
            .entries
            .sortedBy { it.value }
            .joinToString(",") { it.key }
        println("CanonicalDangerous:")
        println(canonicalDangerous)

        return -1
    }
}