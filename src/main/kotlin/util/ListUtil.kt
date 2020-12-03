package util

fun List<Long>.product(): Long = this.fold(1, { p, i -> p * i })