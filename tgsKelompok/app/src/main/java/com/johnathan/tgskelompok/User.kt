package com.johnathan.tgskelompok

class User {
    val id: Int
    val name: String
    val nim: String
    val images: String

    constructor(id: Int, name: String, nim: String, images: String) {
        this.id = id
        this.name = name
        this.nim = nim
        this.images = images
    }
}