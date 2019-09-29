package com.example.looquiz

class sectionFAQ {

    private lateinit var name: String

    private var chapterList: MutableList<chapterFAQ> = mutableListOf()

    constructor(name: String, chapterList: MutableList<chapterFAQ>)
    {
        this.name = name
        this.chapterList = chapterList
    }

    public fun getName() : String
    {
        return name
    }

    public fun setName(name: String)
    {
        this.name = name
    }

    public fun getChapterList() : MutableList<chapterFAQ>
    {
        return chapterList
    }

    public fun setChapterList(chapterList: MutableList<chapterFAQ>)
    {
        this.chapterList = chapterList
    }
}