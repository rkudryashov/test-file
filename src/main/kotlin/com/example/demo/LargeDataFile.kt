package com.example.demo

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

/**
 * Large data processing service for handling various data operations
 * This file contains multiple classes and functions to demonstrate
 * a substantial Kotlin codebase with approximately 25-30k characters
 */
@Component
class LargeDataProcessor {
    
    private val dataCache = mutableMapOf<String, Any>()
    private val processingQueue = mutableListOf<DataTask>()
    
    /**
     * Processes large datasets with various transformation operations
     */
    fun processLargeDataset(data: List<Map<String, Any>>): ProcessedResult {
        val startTime = System.currentTimeMillis()
        val results = mutableListOf<ProcessedItem>()
        
        data.forEachIndexed { index, item ->
            val processedItem = processDataItem(item, index)
            results.add(processedItem)
            
            // Simulate complex processing logic
            if (index % 100 == 0) {
                Thread.sleep(1) // Simulate processing time
            }
        }
        
        val endTime = System.currentTimeMillis()
        return ProcessedResult(
            items = results,
            processingTime = endTime - startTime,
            totalItems = data.size
        )
    }
    
    private fun processDataItem(item: Map<String, Any>, index: Int): ProcessedItem {
        val transformedData = mutableMapOf<String, Any>()
        
        item.forEach { (key, value) ->
            when (value) {
                is String -> transformedData[key] = value.uppercase()
                is Number -> transformedData[key] = value.toDouble() * 1.1
                is Boolean -> transformedData[key] = !value
                else -> transformedData[key] = value
            }
        }
        
        return ProcessedItem(
            id = UUID.randomUUID().toString(),
            originalIndex = index,
            data = transformedData,
            processedAt = LocalDateTime.now(),
            metadata = generateMetadata(item)
        )
    }
    
    private fun generateMetadata(item: Map<String, Any>): Map<String, Any> {
        return mapOf(
            "size" to item.size,
            "hasNumericValues" to item.values.any { it is Number },
            "hasStringValues" to item.values.any { it is String },
            "complexity" to calculateComplexity(item),
            "hash" to item.hashCode()
        )
    }
    
    private fun calculateComplexity(item: Map<String, Any>): Int {
        var complexity = 0
        item.values.forEach { value ->
            when (value) {
                is String -> complexity += value.length
                is Number -> complexity += value.toInt()
                is Map<*, *> -> complexity += (value as Map<*, *>).size * 2
                is List<*> -> complexity += (value as List<*>).size
                else -> complexity += 1
            }
        }
        return complexity
    }
    
    /**
     * Validates data integrity across multiple dimensions
     */
    fun validateDataIntegrity(data: List<Map<String, Any>>): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        val warnings = mutableListOf<ValidationWarning>()
        
        data.forEachIndexed { index, item ->
            // Check for required fields
            if (!item.containsKey("id")) {
                errors.add(ValidationError(
                    type = "MISSING_ID",
                    message = "Item at index $index is missing required 'id' field",
                    itemIndex = index
                ))
            }
            
            // Check for data type consistency
            item.forEach { (key, value) ->
                when (key) {
                    "timestamp" -> {
                        if (value !is String && value !is Number) {
                            warnings.add(ValidationWarning(
                                type = "INVALID_TIMESTAMP",
                                message = "Field '$key' at index $index has unexpected type",
                                itemIndex = index,
                                fieldName = key
                            ))
                        }
                    }
                    "amount" -> {
                        if (value !is Number) {
                            errors.add(ValidationError(
                                type = "INVALID_AMOUNT",
                                message = "Field '$key' at index $index must be a number",
                                itemIndex = index,
                                fieldName = key
                            ))
                        }
                    }
                }
            }
            
            // Check for data range validity
            validateDataRanges(item, index, errors, warnings)
        }
        
        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors,
            warnings = warnings,
            totalItems = data.size
        )
    }
    
    private fun validateDataRanges(
        item: Map<String, Any>,
        index: Int,
        errors: MutableList<ValidationError>,
        warnings: MutableList<ValidationWarning>
    ) {
        item.forEach { (key, value) ->
            when (key) {
                "age" -> {
                    if (value is Number) {
                        val age = value.toInt()
                        if (age < 0 || age > 150) {
                            errors.add(ValidationError(
                                type = "INVALID_AGE_RANGE",
                                message = "Age $age at index $index is outside valid range (0-150)",
                                itemIndex = index,
                                fieldName = key
                            ))
                        }
                    }
                }
                "score" -> {
                    if (value is Number) {
                        val score = value.toDouble()
                        if (score < 0.0 || score > 100.0) {
                            warnings.add(ValidationWarning(
                                type = "SCORE_OUT_OF_RANGE",
                                message = "Score $score at index $index is outside typical range (0-100)",
                                itemIndex = index,
                                fieldName = key
                            ))
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Generates synthetic data for testing purposes
     */
    fun generateSyntheticData(count: Int): List<Map<String, Any>> {
        val data = mutableListOf<Map<String, Any>>()
        val random = Random(System.currentTimeMillis())
        
        repeat(count) { index ->
            val item = mapOf(
                "id" to "item_${index + 1}",
                "name" to generateRandomName(random),
                "age" to random.nextInt(18, 80),
                "score" to random.nextDouble(0.0, 100.0),
                "timestamp" to LocalDateTime.now().minusDays(random.nextLong(0, 365)),
                "category" to generateRandomCategory(random),
                "isActive" to random.nextBoolean(),
                "metadata" to generateRandomMetadata(random)
            )
            data.add(item)
        }
        
        return data
    }
    
    private fun generateRandomName(random: Random): String {
        val firstNames = listOf(
            "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Henry",
            "Ivy", "Jack", "Kate", "Liam", "Maya", "Noah", "Olivia", "Paul",
            "Quinn", "Ruby", "Sam", "Tara", "Uma", "Victor", "Wendy", "Xavier",
            "Yara", "Zoe", "Adam", "Beth", "Chris", "Dana", "Eric", "Fiona"
        )
        val lastNames = listOf(
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
            "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez",
            "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
            "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark",
            "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King"
        )
        
        return "${firstNames.random(random)} ${lastNames.random(random)}"
    }
    
    private fun generateRandomCategory(random: Random): String {
        val categories = listOf(
            "Technology", "Healthcare", "Finance", "Education", "Retail",
            "Manufacturing", "Transportation", "Energy", "Entertainment",
            "Sports", "Food", "Travel", "Real Estate", "Automotive"
        )
        return categories.random(random)
    }
    
    private fun generateRandomMetadata(random: Random): Map<String, Any> {
        return mapOf(
            "priority" to random.nextInt(1, 6),
            "tags" to generateRandomTags(random),
            "location" to generateRandomLocation(random),
            "createdBy" to "system_${random.nextInt(1, 100)}",
            "version" to random.nextDouble(1.0, 5.0)
        )
    }
    
    private fun generateRandomTags(random: Random): List<String> {
        val allTags = listOf(
            "urgent", "important", "review", "approved", "pending",
            "high-priority", "low-priority", "feature", "bug", "enhancement",
            "documentation", "testing", "production", "staging", "development"
        )
        val tagCount = random.nextInt(1, 4)
        return allTags.shuffled(random).take(tagCount)
    }
    
    private fun generateRandomLocation(random: Random): Map<String, Any> {
        val cities = listOf(
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
            "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
            "Austin", "Jacksonville", "Fort Worth", "Columbus", "Charlotte"
        )
        val countries = listOf("USA", "Canada", "Mexico", "UK", "Germany", "France", "Japan", "Australia")
        
        return mapOf(
            "city" to cities.random(random),
            "country" to countries.random(random),
            "coordinates" to mapOf(
                "latitude" to random.nextDouble(-90.0, 90.0),
                "longitude" to random.nextDouble(-180.0, 180.0)
            )
        )
    }
    
    /**
     * Performs batch operations on data with error handling
     */
    fun performBatchOperations(
        operations: List<BatchOperation>,
        data: List<Map<String, Any>>
    ): BatchResult {
        val results = mutableListOf<OperationResult>()
        val errors = mutableListOf<OperationError>()
        
        operations.forEachIndexed { opIndex, operation ->
            try {
                val result = executeOperation(operation, data)
                results.add(result)
            } catch (e: Exception) {
                errors.add(OperationError(
                    operationIndex = opIndex,
                    operationType = operation.type,
                    errorMessage = e.message ?: "Unknown error",
                    exception = e
                ))
            }
        }
        
        return BatchResult(
            successfulOperations = results,
            failedOperations = errors,
            totalOperations = operations.size
        )
    }
    
    private fun executeOperation(operation: BatchOperation, data: List<Map<String, Any>>): OperationResult {
        return when (operation.type) {
            "FILTER" -> executeFilterOperation(operation, data)
            "TRANSFORM" -> executeTransformOperation(operation, data)
            "AGGREGATE" -> executeAggregateOperation(operation, data)
            "VALIDATE" -> executeValidateOperation(operation, data)
            else -> throw IllegalArgumentException("Unknown operation type: ${operation.type}")
        }
    }
    
    private fun executeFilterOperation(operation: BatchOperation, data: List<Map<String, Any>>): OperationResult {
        val filteredData = data.filter { item ->
            operation.conditions.all { condition ->
                evaluateCondition(condition, item)
            }
        }
        
        return OperationResult(
            type = "FILTER",
            inputCount = data.size,
            outputCount = filteredData.size,
            data = filteredData,
            executionTime = System.currentTimeMillis()
        )
    }
    
    private fun executeTransformOperation(operation: BatchOperation, data: List<Map<String, Any>>): OperationResult {
        val transformedData = data.map { item ->
            val transformedItem = mutableMapOf<String, Any>()
            transformedItem.putAll(item)
            
            operation.transformations.forEach { transformation ->
                when (transformation.type) {
                    "UPPERCASE" -> {
                        val field = transformation.field
                        if (item[field] is String) {
                            transformedItem[field] = (item[field] as String).uppercase()
                        }
                    }
                    "MULTIPLY" -> {
                        val field = transformation.field
                        val factor = transformation.value as? Number ?: 1.0
                        if (item[field] is Number) {
                            transformedItem[field] = (item[field] as Number).toDouble() * factor.toDouble()
                        }
                    }
                    "ADD_PREFIX" -> {
                        val field = transformation.field
                        val prefix = transformation.value as? String ?: ""
                        if (item[field] is String) {
                            transformedItem[field] = prefix + (item[field] as String)
                        }
                    }
                }
            }
            
            transformedItem
        }
        
        return OperationResult(
            type = "TRANSFORM",
            inputCount = data.size,
            outputCount = transformedData.size,
            data = transformedData,
            executionTime = System.currentTimeMillis()
        )
    }
    
    private fun executeAggregateOperation(operation: BatchOperation, data: List<Map<String, Any>>): OperationResult {
        val aggregatedData = mutableMapOf<String, Any>()
        
        operation.aggregations.forEach { aggregation ->
            when (aggregation.type) {
                "SUM" -> {
                    val field = aggregation.field
                    val sum = data.filter { it[field] is Number }
                        .sumOf { (it[field] as Number).toDouble() }
                    aggregatedData["sum_$field"] = sum
                }
                "AVERAGE" -> {
                    val field = aggregation.field
                    val numbers = data.filter { it[field] is Number }
                        .map { (it[field] as Number).toDouble() }
                    if (numbers.isNotEmpty()) {
                        aggregatedData["avg_$field"] = numbers.average()
                    }
                }
                "COUNT" -> {
                    val field = aggregation.field
                    val count = data.count { it.containsKey(field) }
                    aggregatedData["count_$field"] = count
                }
                "MAX" -> {
                    val field = aggregation.field
                    val max = data.filter { it[field] is Number }
                        .maxOfOrNull { (it[field] as Number).toDouble() }
                    if (max != null) {
                        aggregatedData["max_$field"] = max
                    }
                }
                "MIN" -> {
                    val field = aggregation.field
                    val min = data.filter { it[field] is Number }
                        .minOfOrNull { (it[field] as Number).toDouble() }
                    if (min != null) {
                        aggregatedData["min_$field"] = min
                    }
                }
            }
        }
        
        return OperationResult(
            type = "AGGREGATE",
            inputCount = data.size,
            outputCount = 1,
            data = listOf(aggregatedData),
            executionTime = System.currentTimeMillis()
        )
    }
    
    private fun executeValidateOperation(operation: BatchOperation, data: List<Map<String, Any>>): OperationResult {
        val validationResult = validateDataIntegrity(data)
        
        return OperationResult(
            type = "VALIDATE",
            inputCount = data.size,
            outputCount = if (validationResult.isValid) data.size else 0,
            data = if (validationResult.isValid) data else emptyList(),
            executionTime = System.currentTimeMillis(),
            metadata = mapOf(
                "isValid" to validationResult.isValid,
                "errorCount" to validationResult.errors.size,
                "warningCount" to validationResult.warnings.size
            )
        )
    }
    
    private fun evaluateCondition(condition: Condition, item: Map<String, Any>): Boolean {
        val value = item[condition.field]
        
        return when (condition.operator) {
            "EQUALS" -> value == condition.value
            "NOT_EQUALS" -> value != condition.value
            "GREATER_THAN" -> {
                if (value is Number && condition.value is Number) {
                    value.toDouble() > condition.value.toDouble()
                } else false
            }
            "LESS_THAN" -> {
                if (value is Number && condition.value is Number) {
                    value.toDouble() < condition.value.toDouble()
                } else false
            }
            "CONTAINS" -> {
                if (value is String && condition.value is String) {
                    value.contains(condition.value)
                } else false
            }
            "STARTS_WITH" -> {
                if (value is String && condition.value is String) {
                    value.startsWith(condition.value)
                } else false
            }
            "ENDS_WITH" -> {
                if (value is String && condition.value is String) {
                    value.endsWith(condition.value)
                } else false
            }
            "IN" -> {
                if (condition.value is List<*>) {
                    condition.value.contains(value)
                } else false
            }
            else -> false
        }
    }
    
    /**
     * Caches processed data for performance optimization
     */
    fun cacheData(key: String, data: Any) {
        dataCache[key] = data
    }
    
    fun getCachedData(key: String): Any? {
        return dataCache[key]
    }
    
    fun clearCache() {
        dataCache.clear()
    }
    
    fun getCacheSize(): Int {
        return dataCache.size
    }
    
    /**
     * Manages processing queue for asynchronous operations
     */
    fun addToQueue(task: DataTask) {
        processingQueue.add(task)
    }
    
    fun processQueue(): List<TaskResult> {
        val results = mutableListOf<TaskResult>()
        
        while (processingQueue.isNotEmpty()) {
            val task = processingQueue.removeAt(0)
            try {
                val result = executeTask(task)
                results.add(result)
            } catch (e: Exception) {
                results.add(TaskResult(
                    taskId = task.id,
                    success = false,
                    error = e.message,
                    executionTime = System.currentTimeMillis()
                ))
            }
        }
        
        return results
    }
    
    private fun executeTask(task: DataTask): TaskResult {
        val startTime = System.currentTimeMillis()
        
        // Simulate task execution
        Thread.sleep(task.estimatedDuration)
        
        val endTime = System.currentTimeMillis()
        
        return TaskResult(
            taskId = task.id,
            success = true,
            result = "Task ${task.id} completed successfully",
            executionTime = endTime - startTime
        )
    }
}

// Data classes for the large data processor
data class ProcessedResult(
    val items: List<ProcessedItem>,
    val processingTime: Long,
    val totalItems: Int
)

data class ProcessedItem(
    val id: String,
    val originalIndex: Int,
    val data: Map<String, Any>,
    val processedAt: LocalDateTime,
    val metadata: Map<String, Any>
)

data class ValidationResult(
    val isValid: Boolean,
    val errors: List<ValidationError>,
    val warnings: List<ValidationWarning>,
    val totalItems: Int
)

data class ValidationError(
    val type: String,
    val message: String,
    val itemIndex: Int,
    val fieldName: String? = null
)

data class ValidationWarning(
    val type: String,
    val message: String,
    val itemIndex: Int,
    val fieldName: String? = null
)

data class BatchOperation(
    val type: String,
    val conditions: List<Condition> = emptyList(),
    val transformations: List<Transformation> = emptyList(),
    val aggregations: List<Aggregation> = emptyList()
)

data class Condition(
    val field: String,
    val operator: String,
    val value: Any
)

data class Transformation(
    val type: String,
    val field: String,
    val value: Any
)

data class Aggregation(
    val type: String,
    val field: String
)

data class BatchResult(
    val successfulOperations: List<OperationResult>,
    val failedOperations: List<OperationError>,
    val totalOperations: Int
)

data class OperationResult(
    val type: String,
    val inputCount: Int,
    val outputCount: Int,
    val data: List<Map<String, Any>>,
    val executionTime: Long,
    val metadata: Map<String, Any> = emptyMap()
)

data class OperationError(
    val operationIndex: Int,
    val operationType: String,
    val errorMessage: String,
    val exception: Exception
)

data class DataTask(
    val id: String,
    val type: String,
    val data: Map<String, Any>,
    val estimatedDuration: Long
)

data class TaskResult(
    val taskId: String,
    val success: Boolean,
    val result: String? = null,
    val error: String? = null,
    val executionTime: Long
)

/**
 * Utility class for data formatting and conversion
 */
class DataFormatter {
    
    fun formatAsJson(data: Any): String {
        return when (data) {
            is Map<*, *> -> formatMapAsJson(data as Map<String, Any>)
            is List<*> -> formatListAsJson(data as List<Any>)
            is String -> "\"$data\""
            is Number -> data.toString()
            is Boolean -> data.toString()
            else -> "\"${data.toString()}\""
        }
    }
    
    private fun formatMapAsJson(map: Map<String, Any>): String {
        val entries = map.entries.joinToString(",") { (key, value) ->
            "\"$key\":${formatAsJson(value)}"
        }
        return "{$entries}"
    }
    
    private fun formatListAsJson(list: List<Any>): String {
        val items = list.joinToString(",") { formatAsJson(it) }
        return "[$items]"
    }
    
    fun formatAsCsv(data: List<Map<String, Any>>): List<String> {
        if (data.isEmpty()) return listOf()
        
        val headers = data.first().keys.joinToString(",")
        val rows = data.map { row ->
            row.values.joinToString(",") { value ->
                when (value) {
                    is String -> "\"${value.replace("\"", "\"\"")}\""
                    else -> value.toString()
                }
            }
        }
        
        return listOf(headers) + rows
    }
    
    fun convertToXml(data: Map<String, Any>, rootElement: String = "data"): String {
        val builder = StringBuilder()
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
        builder.append("<$rootElement>\n")
        
        data.forEach { (key, value) ->
            builder.append("  <$key>")
            when (value) {
                is Map<*, *> -> builder.append(convertToXml(value as Map<String, Any>, key))
                is List<*> -> {
                    (value as List<Any>).forEach { item ->
                        builder.append("    <item>${formatXmlValue(item)}</item>\n")
                    }
                }
                else -> builder.append(formatXmlValue(value))
            }
            builder.append("</$key>\n")
        }
        
        builder.append("</$rootElement>")
        return builder.toString()
    }
    
    private fun formatXmlValue(value: Any): String {
        return when (value) {
            is String -> value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;")
            else -> value.toString()
        }
    }
}

/**
 * Configuration class for data processing settings
 */
data class ProcessingConfig(
    val batchSize: Int = 1000,
    val maxRetries: Int = 3,
    val timeoutMs: Long = 30000,
    val enableCaching: Boolean = true,
    val cacheSize: Int = 10000,
    val enableValidation: Boolean = true,
    val enableLogging: Boolean = true,
    val parallelProcessing: Boolean = false,
    val maxThreads: Int = 4
)

/**
 * Service for managing data processing configurations
 */
@Component
class ConfigService {
    
    private val configs = mutableMapOf<String, ProcessingConfig>()
    
    fun getConfig(name: String): ProcessingConfig {
        return configs[name] ?: ProcessingConfig()
    }
    
    fun setConfig(name: String, config: ProcessingConfig) {
        configs[name] = config
    }
    
    fun getAllConfigs(): Map<String, ProcessingConfig> {
        return configs.toMap()
    }
    
    fun removeConfig(name: String) {
        configs.remove(name)
    }
    
    fun resetToDefaults() {
        configs.clear()
    }
}
