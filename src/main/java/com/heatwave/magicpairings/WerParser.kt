package com.heatwave.magicpairings

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

import org.w3c.dom.*
import org.xml.sax.ErrorHandler
import org.xml.sax.SAXException
import org.xml.sax.SAXParseException
import org.xml.sax.helpers.*

import java.io.*
import java.util.ArrayList
import java.util.HashMap

object WerParser {

    fun parse(`is`: InputStream): Round {
        val dbf = DocumentBuilderFactory.newInstance()
        var db: DocumentBuilder? = null
        try {
            db = dbf.newDocumentBuilder()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        }

        var doc: Document? = null
        try {
            doc = db!!.parse(`is`)
        } catch (e: SAXException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val participationNode = doc!!.documentElement.getElementsByTagName("participation").item(0) as Element
        val players = participationNode.getElementsByTagName("person")
        val playerIds = HashMap<Int, Player>()
        for (i in 0 until players.length) {
            val el = players.item(i) as Element
            val id = Integer.parseInt(el.getAttribute("id"))
            val firstName = el.getAttribute("first")
            val lastName = el.getAttribute("last")
            playerIds[id] = Player(id, firstName, lastName)
        }

        val seats = doc!!.documentElement.getElementsByTagName("seats").item(0) as Element
        val tablesNode = seats.getElementsByTagName("table")
        val tables = ArrayList<Table>()
        for (i in 0 until tablesNode.length) {
            val el = tablesNode.item(i) as Element
            val tableNumber = Integer.parseInt(el.getAttribute("number"))
            val pair = el.getElementsByTagName("seat")
            val seat1 = Integer.parseInt((pair.item(0) as Element).getAttribute("player"))
            val seat2 = Integer.parseInt((pair.item(1) as Element).getAttribute("player"))
            tables.add(Table(tableNumber, seat1, seat2))
        }
        val res = Round(0, tables, playerIds)
        return res
    }
}
