package com.example.looquiz

class sectionNotice {

    private lateinit var name: String

    private var chapterList: MutableList<chapterNotice> = mutableListOf()

    constructor(name: String, chapterList: MutableList<chapterNotice>)
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

    public fun getChapterList() : MutableList<chapterNotice>
    {
        return chapterList
    }

    public fun setChapterList(chapterList: MutableList<chapterNotice>)
    {
        this.chapterList = chapterList
    }
}