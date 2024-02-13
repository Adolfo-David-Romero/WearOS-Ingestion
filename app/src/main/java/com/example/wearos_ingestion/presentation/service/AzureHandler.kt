package com.example.wearos_ingestion.presentation.service

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class AzureHandler {
    private val TAG = "AzureHandler"

    private val serverName = "pcc-informal-care.database.windows.net"
    private val databaseName = "informalcare_sso"
    private val username = "informal-admin"
    private val password = "vesBib-qirned-byrdy9"

    private fun establishConnection(): Connection? {
        var connection: Connection? = null
        val url = "jdbc:sqlserver://$serverName;databaseName=$databaseName;user=$username;password=$password;"
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            connection = DriverManager.getConnection(url)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return connection
    }
    fun sendDataToAzureSQL(sensorData: String) {
        val connection = establishConnection()
        if (connection != null) {
            try {
                val statement = connection.createStatement()
                val query = "INSERT INTO sensorData (data) VALUES ('$sensorData')"
                statement.executeUpdate(query)

                // Log that data has been sent to Azure SQL
                Log.d(TAG, "Sensor Data Sent to Azure SQL")

                // Close the connection
                connection.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        } else {
            Log.e(TAG, "Failed to establish connection to Azure SQL Database")
        }
    }
}