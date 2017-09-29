package com.heatwave.magicpairings

import org.apache.commons.fileupload.FileUploadException
import org.apache.commons.fileupload.servlet.ServletFileUpload
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger
import javax.json.Json
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Servlet implementation class UploadServlet
 */
class UploadServlet : HttpServlet() {
    val log = Logger.getLogger(UploadServlet::class.java.name)

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {

        // Check that we have a file upload request
        val isMultipart = ServletFileUpload.isMultipartContent(request)
        if (!isMultipart) {
            return
        }

        // Create a new file upload handler
        val upload = ServletFileUpload()

        // Parse the request
        try {
            val iter = upload.getItemIterator(request)
            while (iter.hasNext()) {
                val item = iter.next()
                val rd = WerParser.parse(item.openStream())
                postPairings(rd)
            }
        } catch (e: FileUploadException) {
            e.printStackTrace()
        }

        // displays done.jsp page after upload finished
        servletContext.getRequestDispatcher("/done.jsp").forward(request, response)
    }

    private fun postPairings(round: Round) {
        val url = URL("https://magicpairings.firebaseio.com/user.json")
        val conn = url.openConnection() as HttpURLConnection
        conn.doOutput = true
        conn.setRequestProperty("X-HTTP-Method-Override", "PATCH")
        conn.requestMethod = "POST"

        val json = Json.createObjectBuilder()
        round.tables.forEach {
            json.add(it.first.toString(), it.second)
            json.add(it.second.toString(), it.first)
        }

        val writer = OutputStreamWriter(conn.outputStream)
        writer.write(json.build().toString())
        writer.close()

        val respCode = conn.responseCode // New items get NOT_FOUND on PUT
        log.info(respCode.toString())
//        println(respCode.toString())
    }

    companion object {
        private val serialVersionUID = 1L
    }
}