package com.heatwave.magicpairings

data class Table(val number: Int, val first: Int, val second: Int)

data class Player(val dci: Int, val firstName: String, val lastName: String) {
    override fun toString(): String {
        return firstName + " " + lastName
    }
}

data class Round(val number: Int, val tables: List<Table>, val players: Map<Int, Player>)