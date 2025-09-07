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

    fun test() {

    }

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
                errors.add(
                    ValidationError(
                        type = "MISSING_ID",
                        message = "Item at index $index is missing required 'id' field",
                        itemIndex = index
                    )
                )
            }

            // Check for data type consistency
            item.forEach { (key, value) ->
                when (key) {
                    "timestamp" -> {
                        if (value !is String && value !is Number) {
                            warnings.add(
                                ValidationWarning(
                                    type = "INVALID_TIMESTAMP",
                                    message = "Field '$key' at index $index has unexpected type",
                                    itemIndex = index,
                                    fieldName = key
                                )
                            )
                        }
                    }

                    "amount" -> {
                        if (value !is Number) {
                            errors.add(
                                ValidationError(
                                    type = "INVALID_AMOUNT",
                                    message = "Field '$key' at index $index must be a number",
                                    itemIndex = index,
                                    fieldName = key
                                )
                            )
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
                            errors.add(
                                ValidationError(
                                    type = "INVALID_AGE_RANGE",
                                    message = "Age $age at index $index is outside valid range (0-150)",
                                    itemIndex = index,
                                    fieldName = key
                                )
                            )
                        }
                    }
                }

                "score" -> {
                    if (value is Number) {
                        val score = value.toDouble()
                        if (score < 0.0 || score > 100.0) {
                            warnings.add(
                                ValidationWarning(
                                    type = "SCORE_OUT_OF_RANGE",
                                    message = "Score $score at index $index is outside typical range (0-100)",
                                    itemIndex = index,
                                    fieldName = key
                                )
                            )
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
                errors.add(
                    OperationError(
                        operationIndex = opIndex,
                        operationType = operation.type,
                        errorMessage = e.message ?: "Unknown error",
                        exception = e
                    )
                )
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
                results.add(
                    TaskResult(
                        taskId = task.id,
                        success = false,
                        error = e.message,
                        executionTime = System.currentTimeMillis()
                    )
                )
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

object TestObject {
    const val TEST = 1
}

enum class TaskStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
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

/**
 * Advanced data analytics service for complex data analysis operations
 */
@Component
class DataAnalyticsService {

    private val analyticsCache = mutableMapOf<String, AnalyticsResult>()
    private val metricsCollector = MetricsCollector()

    /**
     * Performs comprehensive statistical analysis on datasets
     */
    fun performStatisticalAnalysis(data: List<Map<String, Any>>): StatisticalAnalysisResult {
        val startTime = System.currentTimeMillis()

        val numericFields = extractNumericFields(data)
        val categoricalFields = extractCategoricalFields(data)
        val statisticalMeasures = calculateStatisticalMeasures(data, numericFields)
        val correlationMatrix = calculateCorrelationMatrix(data, numericFields)
        val distributionAnalysis = analyzeDistributions(data, numericFields)
        val outlierDetection = detectOutliers(data, numericFields)

        val endTime = System.currentTimeMillis()

        return StatisticalAnalysisResult(
            numericFields = numericFields,
            categoricalFields = categoricalFields,
            statisticalMeasures = statisticalMeasures,
            correlationMatrix = correlationMatrix,
            distributionAnalysis = distributionAnalysis,
            outlierDetection = outlierDetection,
            processingTime = endTime - startTime,
            totalRecords = data.size
        )
    }

    private fun extractNumericFields(data: List<Map<String, Any>>): List<String> {
        if (data.isEmpty()) return emptyList()

        val fieldTypes = mutableMapOf<String, MutableSet<String>>()

        data.forEach { record ->
            record.forEach { (field, value) ->
                val type = when (value) {
                    is Number -> "numeric"
                    is String -> "string"
                    is Boolean -> "boolean"
                    else -> "other"
                }
                fieldTypes.getOrPut(field) { mutableSetOf() }.add(type)
            }
        }

        return fieldTypes.filter { (_, types) ->
            types.size == 1 && types.contains("numeric")
        }.keys.toList()
    }

    private fun extractCategoricalFields(data: List<Map<String, Any>>): List<String> {
        if (data.isEmpty()) return emptyList()

        val fieldTypes = mutableMapOf<String, MutableSet<String>>()

        data.forEach { record ->
            record.forEach { (field, value) ->
                val type = when (value) {
                    is Number -> "numeric"
                    is String -> "string"
                    is Boolean -> "boolean"
                    else -> "other"
                }
                fieldTypes.getOrPut(field) { mutableSetOf() }.add(type)
            }
        }

        return fieldTypes.filter { (_, types) ->
            types.size == 1 && (types.contains("string") || types.contains("boolean"))
        }.keys.toList()
    }

    private fun calculateStatisticalMeasures(
        data: List<Map<String, Any>>,
        numericFields: List<String>
    ): Map<String, StatisticalMeasures> {
        val measures = mutableMapOf<String, StatisticalMeasures>()

        numericFields.forEach { field ->
            val values = data.mapNotNull { it[field] as? Number }
                .map { it.toDouble() }

            if (values.isNotEmpty()) {
                measures[field] = StatisticalMeasures(
                    mean = values.average(),
                    median = calculateMedian(values),
                    mode = calculateMode(values),
                    standardDeviation = calculateStandardDeviation(values),
                    variance = calculateVariance(values),
                    min = values.minOrNull() ?: 0.0,
                    max = values.maxOrNull() ?: 0.0,
                    range = (values.maxOrNull() ?: 0.0) - (values.minOrNull() ?: 0.0),
                    quartiles = calculateQuartiles(values),
                    skewness = calculateSkewness(values),
                    kurtosis = calculateKurtosis(values)
                )
            }
        }

        return measures
    }

    private fun calculateMedian(values: List<Double>): Double {
        val sorted = values.sorted()
        val size = sorted.size
        return if (size % 2 == 0) {
            (sorted[size / 2 - 1] + sorted[size / 2]) / 2.0
        } else {
            sorted[size / 2]
        }
    }

    private fun calculateMode(values: List<Double>): Double? {
        val frequencyMap = values.groupingBy { it }.eachCount()
        val maxFrequency = frequencyMap.values.maxOrNull()
        return frequencyMap.entries.find { it.value == maxFrequency }?.key
    }

    private fun calculateStandardDeviation(values: List<Double>): Double {
        val mean = values.average()
        val variance = values.map { Math.pow(it - mean, 2.0) }.average()
        return kotlin.math.sqrt(variance)
    }

    private fun calculateVariance(values: List<Double>): Double {
        val mean = values.average()
        return values.map { Math.pow(it - mean, 2.0) }.average()
    }

    private fun calculateQuartiles(values: List<Double>): Quartiles {
        val sorted = values.sorted()
        val size = sorted.size

        val q1 = if (size % 4 == 0) {
            (sorted[size / 4 - 1] + sorted[size / 4]) / 2.0
        } else {
            sorted[size / 4]
        }

        val q2 = calculateMedian(sorted)

        val q3 = if (size % 4 == 0) {
            (sorted[3 * size / 4 - 1] + sorted[3 * size / 4]) / 2.0
        } else {
            sorted[3 * size / 4]
        }

        return Quartiles(q1, q2, q3)
    }

    private fun calculateSkewness(values: List<Double>): Double {
        val mean = values.average()
        val stdDev = calculateStandardDeviation(values)
        val n = values.size

        val skewness = values.map { Math.pow((it - mean) / stdDev, 3.0) }.sum() / n
        return skewness
    }

    private fun calculateKurtosis(values: List<Double>): Double {
        val mean = values.average()
        val stdDev = calculateStandardDeviation(values)
        val n = values.size

        val kurtosis = values.map { Math.pow((it - mean) / stdDev, 4.0) }.sum() / n - 3
        return kurtosis
    }

    private fun calculateCorrelationMatrix(
        data: List<Map<String, Any>>,
        numericFields: List<String>
    ): Map<String, Map<String, Double>> {
        val matrix = mutableMapOf<String, MutableMap<String, Double>>()

        numericFields.forEach { field1 ->
            matrix[field1] = mutableMapOf()
            numericFields.forEach { field2 ->
                val correlation = calculateCorrelation(data, field1, field2)
                matrix[field1]!![field2] = correlation
            }
        }

        return matrix
    }

    private fun calculateCorrelation(
        data: List<Map<String, Any>>,
        field1: String,
        field2: String
    ): Double {
        val values1 = data.mapNotNull { it[field1] as? Number }.map { it.toDouble() }
        val values2 = data.mapNotNull { it[field2] as? Number }.map { it.toDouble() }

        if (values1.size != values2.size || values1.isEmpty()) return 0.0

        val mean1 = values1.average()
        val mean2 = values2.average()

        val numerator = values1.zip(values2).sumOf { (v1, v2) ->
            (v1 - mean1) * (v2 - mean2)
        }

        val denominator = kotlin.math.sqrt(
            values1.sumOf { Math.pow(it - mean1, 2.0) } * values2.sumOf { Math.pow(it - mean2, 2.0) }
        )

        return if (denominator != 0.0) numerator / denominator else 0.0
    }

    private fun analyzeDistributions(
        data: List<Map<String, Any>>,
        numericFields: List<String>
    ): Map<String, DistributionAnalysis> {
        val distributions = mutableMapOf<String, DistributionAnalysis>()

        numericFields.forEach { field ->
            val values = data.mapNotNull { it[field] as? Number }.map { it.toDouble() }

            if (values.isNotEmpty()) {
                val histogram = createHistogram(values, 10)
                val isNormal = testNormality(values)
                val distributionType = classifyDistribution(values)

                distributions[field] = DistributionAnalysis(
                    histogram = histogram,
                    isNormal = isNormal,
                    distributionType = distributionType,
                    entropy = calculateEntropy(histogram)
                )
            }
        }

        return distributions
    }

    private fun createHistogram(values: List<Double>, bins: Int): Map<String, Int> {
        val min = values.minOrNull() ?: 0.0
        val max = values.maxOrNull() ?: 0.0
        val binWidth = (max - min) / bins

        val histogram = mutableMapOf<String, Int>()

        repeat(bins) { i ->
            val binStart = min + i * binWidth
            val binEnd = min + (i + 1) * binWidth
            val binLabel = "[${String.format("%.2f", binStart)}, ${String.format("%.2f", binEnd)})"

            val count = values.count { it >= binStart && it < binEnd }
            histogram[binLabel] = count
        }

        return histogram
    }

    private fun testNormality(values: List<Double>): Boolean {
        // Simplified normality test using skewness and kurtosis
        val skewness = calculateSkewness(values)
        val kurtosis = calculateKurtosis(values)

        // Consider normal if skewness is close to 0 and kurtosis is close to 0
        return kotlin.math.abs(skewness) < 0.5 && kotlin.math.abs(kurtosis) < 0.5
    }

    private fun classifyDistribution(values: List<Double>): String {
        val skewness = calculateSkewness(values)
        val kurtosis = calculateKurtosis(values)

        return when {
            kotlin.math.abs(skewness) < 0.5 && kotlin.math.abs(kurtosis) < 0.5 -> "Normal"
            skewness > 1.0 -> "Right-skewed"
            skewness < -1.0 -> "Left-skewed"
            kurtosis > 3.0 -> "Heavy-tailed"
            kurtosis < -1.0 -> "Light-tailed"
            else -> "Unknown"
        }
    }

    private fun calculateEntropy(histogram: Map<String, Int>): Double {
        val total = histogram.values.sum()
        if (total == 0) return 0.0

        return -histogram.values.sumOf { count ->
            val probability = count.toDouble() / total
            if (probability > 0) probability * kotlin.math.log2(probability) else 0.0
        }
    }

    private fun detectOutliers(
        data: List<Map<String, Any>>,
        numericFields: List<String>
    ): Map<String, List<Int>> {
        val outliers = mutableMapOf<String, List<Int>>()

        numericFields.forEach { field ->
            val values = data.mapNotNull { it[field] as? Number }.map { it.toDouble() }

            if (values.size > 4) { // Need at least 5 values for IQR method
                val q1 = calculateQuartiles(values).q1
                val q3 = calculateQuartiles(values).q3
                val iqr = q3 - q1

                val lowerBound = q1 - 1.5 * iqr
                val upperBound = q3 + 1.5 * iqr

                val outlierIndices = data.mapIndexedNotNull { index, record ->
                    val value = (record[field] as? Number)?.toDouble()
                    if (value != null && (value < lowerBound || value > upperBound)) {
                        index
                    } else null
                }

                outliers[field] = outlierIndices
            }
        }

        return outliers
    }

    /**
     * Performs time series analysis on temporal data
     */
    fun performTimeSeriesAnalysis(
        data: List<Map<String, Any>>,
        timeField: String,
        valueField: String
    ): TimeSeriesAnalysisResult {
        val timeSeriesData = data.mapNotNull { record ->
            val time = record[timeField]
            val value = (record[valueField] as? Number)?.toDouble()

            if (time != null && value != null) {
                TimeSeriesPoint(time, value)
            } else null
        }.sortedBy { it.timestamp.toString() }

        val trend = calculateTrend(timeSeriesData)
        val seasonality = detectSeasonality(timeSeriesData)
        val autocorrelation = calculateAutocorrelation(timeSeriesData)
        val forecast = generateForecast(timeSeriesData, 10)

        return TimeSeriesAnalysisResult(
            data = timeSeriesData,
            trend = trend,
            seasonality = seasonality,
            autocorrelation = autocorrelation,
            forecast = forecast
        )
    }

    private fun calculateTrend(data: List<TimeSeriesPoint>): TrendAnalysis {
        if (data.size < 2) return TrendAnalysis("Insufficient data", 0.0, 0.0)

        val n = data.size
        val xValues = (0 until n).map { it.toDouble() }
        val yValues = data.map { it.value }

        val sumX = xValues.sum()
        val sumY = yValues.sum()
        val sumXY = xValues.zip(yValues).sumOf { (x, y) -> x * y }
        val sumXX = xValues.sumOf { it * it }

        val slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX)
        val intercept = (sumY - slope * sumX) / n

        val trendType = when {
            slope > 0.1 -> "Increasing"
            slope < -0.1 -> "Decreasing"
            else -> "Stable"
        }

        return TrendAnalysis(trendType, slope, intercept)
    }

    private fun detectSeasonality(data: List<TimeSeriesPoint>): SeasonalityAnalysis {
        if (data.size < 12) return SeasonalityAnalysis(false, 0, emptyMap())

        // Simple seasonality detection using autocorrelation
        val autocorrelations = mutableMapOf<Int, Double>()

        for (lag in 1..minOf(12, data.size / 2)) {
            val autocorr = calculateAutocorrelationAtLag(data, lag)
            autocorrelations[lag] = autocorr
        }

        val maxAutocorr = autocorrelations.maxByOrNull { it.value }
        val hasSeasonality = maxAutocorr?.value ?: 0.0 > 0.5

        return SeasonalityAnalysis(
            hasSeasonality = hasSeasonality,
            period = if (hasSeasonality) maxAutocorr?.key ?: 0 else 0,
            autocorrelations = autocorrelations
        )
    }

    private fun calculateAutocorrelation(data: List<TimeSeriesPoint>): Map<Int, Double> {
        val autocorrelations = mutableMapOf<Int, Double>()
        val maxLag = minOf(20, data.size / 4)

        for (lag in 1..maxLag) {
            val autocorr = calculateAutocorrelationAtLag(data, lag)
            autocorrelations[lag] = autocorr
        }

        return autocorrelations
    }

    private fun calculateAutocorrelationAtLag(data: List<TimeSeriesPoint>, lag: Int): Double {
        if (data.size <= lag) return 0.0

        val values = data.map { it.value }
        val mean = values.average()

        val numerator = (0 until data.size - lag).sumOf { i ->
            (values[i] - mean) * (values[i + lag] - mean)
        }

        val denominator = values.sumOf { Math.pow(it - mean, 2.0) }

        return if (denominator != 0.0) numerator / denominator else 0.0
    }

    private fun generateForecast(data: List<TimeSeriesPoint>, periods: Int): List<ForecastPoint> {
        if (data.isEmpty()) return emptyList()

        val trend = calculateTrend(data)
        val lastValue = data.last().value
        val lastTime = data.size - 1

        return (1..periods).map { period ->
            val forecastValue = trend.intercept + trend.slope * (lastTime + period)
            ForecastPoint(
                period = period,
                value = forecastValue,
                confidence = calculateForecastConfidence(data, period)
            )
        }
    }

    private fun calculateForecastConfidence(data: List<TimeSeriesPoint>, period: Int): Double {
        // Simplified confidence calculation
        val baseConfidence = 0.8
        val decayFactor = 0.95
        return baseConfidence * Math.pow(decayFactor, period.toDouble())
    }

    /**
     * Performs clustering analysis on data
     */
    fun performClusteringAnalysis(
        data: List<Map<String, Any>>,
        numericFields: List<String>,
        k: Int = 3
    ): ClusteringAnalysisResult {
        val points = data.mapIndexed { index, record ->
            val coordinates = numericFields.mapNotNull { field ->
                (record[field] as? Number)?.toDouble()
            }
            if (coordinates.size == numericFields.size) {
                DataPoint(index, coordinates)
            } else null
        }.filterNotNull()

        if (points.isEmpty()) {
            return ClusteringAnalysisResult(emptyList(), emptyMap(), 0.0)
        }

        val clusters = performKMeansClustering(points, k)
        val silhouetteScore = calculateSilhouetteScore(points, clusters)
        val clusterStatistics = calculateClusterStatistics(clusters, numericFields)

        return ClusteringAnalysisResult(
            clusters = clusters,
            clusterStatistics = clusterStatistics,
            silhouetteScore = silhouetteScore
        )
    }

    private fun performKMeansClustering(points: List<DataPoint>, k: Int): List<Cluster> {
        if (points.isEmpty() || k <= 0) return emptyList()

        // Initialize centroids randomly
        val centroids = mutableListOf<Centroid>()
        val random = Random(System.currentTimeMillis())

        repeat(k) { i ->
            val randomPoint = points.random(random)
            centroids.add(Centroid(i, randomPoint.coordinates))
        }

        var clusters = assignPointsToClusters(points, centroids)
        var iterations = 0
        val maxIterations = 100

        do {
            val newCentroids = calculateNewCentroids(clusters)
            val newClusters = assignPointsToClusters(points, newCentroids)

            if (clustersEqual(clusters, newClusters)) break

            clusters = newClusters
            iterations++
        } while (iterations < maxIterations)

        return clusters
    }

    private fun assignPointsToClusters(points: List<DataPoint>, centroids: List<Centroid>): List<Cluster> {
        val clusters = centroids.map { Cluster(it.id, mutableListOf(), it.coordinates) }

        points.forEach { point ->
            val closestCentroid = centroids.minByOrNull { centroid ->
                calculateDistance(point.coordinates, centroid.coordinates)
            }

            if (closestCentroid != null) {
                val cluster = clusters.find { it.id == closestCentroid.id }
                cluster?.points?.add(point)
            }
        }

        return clusters
    }

    private fun calculateNewCentroids(clusters: List<Cluster>): List<Centroid> {
        return clusters.map { cluster ->
            if (cluster.points.isEmpty()) {
                Centroid(cluster.id, cluster.centroid)
            } else {
                val dimensions = cluster.points.first().coordinates.size
                val newCentroid = (0 until dimensions).map { dim ->
                    cluster.points.map { it.coordinates[dim] }.average()
                }
                Centroid(cluster.id, newCentroid)
            }
        }
    }

    private fun clustersEqual(clusters1: List<Cluster>, clusters2: List<Cluster>): Boolean {
        if (clusters1.size != clusters2.size) return false

        return clusters1.zip(clusters2).all { (c1, c2) ->
            c1.points.size == c2.points.size &&
                    c1.points.map { it.id }.sorted() == c2.points.map { it.id }.sorted()
        }
    }

    private fun calculateDistance(point1: List<Double>, point2: List<Double>): Double {
        if (point1.size != point2.size) return Double.MAX_VALUE

        return kotlin.math.sqrt(
            point1.zip(point2).sumOf { (x1, x2) -> Math.pow(x1 - x2, 2.0) }
        )
    }

    private fun calculateSilhouetteScore(points: List<DataPoint>, clusters: List<Cluster>): Double {
        if (clusters.isEmpty() || points.isEmpty()) return 0.0

        val silhouetteScores = points.map { point ->
            val pointCluster = clusters.find { cluster ->
                cluster.points.any { it.id == point.id }
            }

            if (pointCluster == null) return@map 0.0

            val a = calculateAverageDistanceToCluster(point, pointCluster.points)
            val b = clusters.filter { it.id != pointCluster.id }
                .minOfOrNull { cluster ->
                    calculateAverageDistanceToCluster(point, cluster.points)
                } ?: Double.MAX_VALUE

            if (maxOf(a, b) == 0.0) 0.0 else (b - a) / maxOf(a, b)
        }

        return silhouetteScores.average()
    }

    private fun calculateAverageDistanceToCluster(point: DataPoint, clusterPoints: List<DataPoint>): Double {
        if (clusterPoints.isEmpty()) return 0.0

        val distances = clusterPoints.map { otherPoint ->
            calculateDistance(point.coordinates, otherPoint.coordinates)
        }

        return distances.average()
    }

    private fun calculateClusterStatistics(
        clusters: List<Cluster>,
        numericFields: List<String>
    ): Map<Int, ClusterStatistics> {
        return clusters.associate { cluster ->
            cluster.id to ClusterStatistics(
                size = cluster.points.size,
                averageDistance = calculateAverageIntraClusterDistance(cluster),
                compactness = calculateClusterCompactness(cluster),
                separation = calculateClusterSeparation(cluster, clusters)
            )
        }
    }

    private fun calculateAverageIntraClusterDistance(cluster: Cluster): Double {
        if (cluster.points.size <= 1) return 0.0

        val distances = mutableListOf<Double>()
        for (i in cluster.points.indices) {
            for (j in i + 1 until cluster.points.size) {
                val distance = calculateDistance(
                    cluster.points[i].coordinates,
                    cluster.points[j].coordinates
                )
                distances.add(distance)
            }
        }

        return distances.average()
    }

    private fun calculateClusterCompactness(cluster: Cluster): Double {
        if (cluster.points.isEmpty()) return 0.0

        val distances = cluster.points.map { point ->
            calculateDistance(point.coordinates, cluster.centroid)
        }

        return distances.average()
    }

    private fun calculateClusterSeparation(cluster: Cluster, allClusters: List<Cluster>): Double {
        val otherClusters = allClusters.filter { it.id != cluster.id }
        if (otherClusters.isEmpty()) return 0.0

        val distances = otherClusters.map { otherCluster ->
            calculateDistance(cluster.centroid, otherCluster.centroid)
        }

        return distances.minOrNull() ?: 0.0
    }

    /**
     * Caches analytics results for performance
     */
    fun cacheAnalyticsResult(key: String, result: AnalyticsResult) {
        analyticsCache[key] = result
    }

    fun getCachedAnalyticsResult(key: String): AnalyticsResult? {
        return analyticsCache[key]
    }

    fun clearAnalyticsCache() {
        analyticsCache.clear()
    }

    /**
     * Collects performance metrics
     */
    fun getPerformanceMetrics(): PerformanceMetrics {
        return metricsCollector.getMetrics()
    }
}

// Additional data classes for analytics
data class StatisticalAnalysisResult(
    val numericFields: List<String>,
    val categoricalFields: List<String>,
    val statisticalMeasures: Map<String, StatisticalMeasures>,
    val correlationMatrix: Map<String, Map<String, Double>>,
    val distributionAnalysis: Map<String, DistributionAnalysis>,
    val outlierDetection: Map<String, List<Int>>,
    val processingTime: Long,
    val totalRecords: Int
)

data class StatisticalMeasures(
    val mean: Double,
    val median: Double,
    val mode: Double?,
    val standardDeviation: Double,
    val variance: Double,
    val min: Double,
    val max: Double,
    val range: Double,
    val quartiles: Quartiles,
    val skewness: Double,
    val kurtosis: Double
)

data class Quartiles(
    val q1: Double,
    val q2: Double,
    val q3: Double
)

data class DistributionAnalysis(
    val histogram: Map<String, Int>,
    val isNormal: Boolean,
    val distributionType: String,
    val entropy: Double
)

data class TimeSeriesAnalysisResult(
    val data: List<TimeSeriesPoint>,
    val trend: TrendAnalysis,
    val seasonality: SeasonalityAnalysis,
    val autocorrelation: Map<Int, Double>,
    val forecast: List<ForecastPoint>
)

data class TimeSeriesPoint(
    val timestamp: Any,
    val value: Double
)

data class TrendAnalysis(
    val type: String,
    val slope: Double,
    val intercept: Double
)

data class SeasonalityAnalysis(
    val hasSeasonality: Boolean,
    val period: Int,
    val autocorrelations: Map<Int, Double>
)

data class ForecastPoint(
    val period: Int,
    val value: Double,
    val confidence: Double
)

data class ClusteringAnalysisResult(
    val clusters: List<Cluster>,
    val clusterStatistics: Map<Int, ClusterStatistics>,
    val silhouetteScore: Double
)

data class DataPoint(
    val id: Int,
    val coordinates: List<Double>
)

data class Cluster(
    val id: Int,
    val points: MutableList<DataPoint>,
    val centroid: List<Double>
)

data class Centroid(
    val id: Int,
    val coordinates: List<Double>
)

data class ClusterStatistics(
    val size: Int,
    val averageDistance: Double,
    val compactness: Double,
    val separation: Double
)

data class AnalyticsResult(
    val type: String,
    val data: Any,
    val timestamp: Long,
    val processingTime: Long
)

data class PerformanceMetrics(
    val totalAnalyses: Int,
    val averageProcessingTime: Double,
    val cacheHitRate: Double,
    val memoryUsage: Long
)

/**
 * Metrics collector for performance monitoring
 */
class MetricsCollector {
    private val metrics = mutableMapOf<String, Any>()
    private var totalAnalyses = 0
    private var totalProcessingTime = 0L
    private var cacheHits = 0
    private var cacheRequests = 0

    fun recordAnalysis(processingTime: Long) {
        totalAnalyses++
        totalProcessingTime += processingTime
    }

    fun recordCacheHit() {
        cacheHits++
        cacheRequests++
    }

    fun recordCacheMiss() {
        cacheRequests++
    }

    fun getMetrics(): PerformanceMetrics {
        val averageProcessingTime = if (totalAnalyses > 0) {
            totalProcessingTime.toDouble() / totalAnalyses
        } else 0.0

        val cacheHitRate = if (cacheRequests > 0) {
            cacheHits.toDouble() / cacheRequests
        } else 0.0

        return PerformanceMetrics(
            totalAnalyses = totalAnalyses,
            averageProcessingTime = averageProcessingTime,
            cacheHitRate = cacheHitRate,
            memoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        )
    }

    fun reset() {
        totalAnalyses = 0
        totalProcessingTime = 0L
        cacheHits = 0
        cacheRequests = 0
        metrics.clear()
    }
}

/**
 * Machine learning service for predictive analytics
 */
@Component
class MachineLearningService {

    private val models = mutableMapOf<String, MLModel>()
    private val trainingHistory = mutableListOf<TrainingRecord>()

    /**
     * Trains a linear regression model
     */
    fun trainLinearRegression(
        trainingData: List<Map<String, Any>>,
        targetField: String,
        featureFields: List<String>,
        modelName: String
    ): TrainingResult {
        val startTime = System.currentTimeMillis()

        val features = trainingData.map { record ->
            featureFields.mapNotNull { field ->
                (record[field] as? Number)?.toDouble()
            }
        }.filter { it.size == featureFields.size }

        val targets = trainingData.mapNotNull { record ->
            (record[targetField] as? Number)?.toDouble()
        }

        if (features.size != targets.size || features.isEmpty()) {
            return TrainingResult(
                success = false,
                error = "Invalid training data",
                modelName = modelName,
                trainingTime = 0
            )
        }

        val model = LinearRegressionModel()
        val result = model.train(features, targets)

        models[modelName] = model

        val endTime = System.currentTimeMillis()
        val trainingTime = endTime - startTime

        trainingHistory.add(
            TrainingRecord(
                modelName = modelName,
                modelType = "LinearRegression",
                trainingTime = trainingTime,
                success = result.success,
                error = result.error,
                timestamp = System.currentTimeMillis()
            )
        )

        return TrainingResult(
            success = result.success,
            error = result.error,
            modelName = modelName,
            trainingTime = trainingTime,
            metrics = result.metrics
        )
    }

    /**
     * Makes predictions using a trained model
     */
    fun predict(modelName: String, features: List<Double>): PredictionResult {
        val model = models[modelName]

        return if (model != null) {
            try {
                val prediction = model.predict(features)
                PredictionResult(
                    success = true,
                    prediction = prediction,
                    confidence = model.getConfidence(),
                    modelName = modelName
                )
            } catch (e: Exception) {
                PredictionResult(
                    success = false,
                    error = e.message ?: "Prediction failed",
                    modelName = modelName
                )
            }
        } else {
            PredictionResult(
                success = false,
                error = "Model not found: $modelName",
                modelName = modelName
            )
        }
    }

    /**
     * Evaluates model performance
     */
    fun evaluateModel(
        modelName: String,
        testData: List<Map<String, Any>>,
        targetField: String,
        featureFields: List<String>
    ): ModelEvaluation {
        val model = models[modelName]

        if (model == null) {
            return ModelEvaluation(
                success = false,
                error = "Model not found: $modelName"
            )
        }

        val testFeatures = testData.map { record ->
            featureFields.mapNotNull { field ->
                (record[field] as? Number)?.toDouble()
            }
        }.filter { it.size == featureFields.size }

        val actualTargets = testData.mapNotNull { record ->
            (record[targetField] as? Number)?.toDouble()
        }

        if (testFeatures.size != actualTargets.size || testFeatures.isEmpty()) {
            return ModelEvaluation(
                success = false,
                error = "Invalid test data"
            )
        }

        val predictions = testFeatures.map { features ->
            model.predict(features)
        }

        val mse = calculateMSE(actualTargets, predictions)
        val rmse = kotlin.math.sqrt(mse)
        val mae = calculateMAE(actualTargets, predictions)
        val r2 = calculateR2(actualTargets, predictions)

        return ModelEvaluation(
            success = true,
            mse = mse,
            rmse = rmse,
            mae = mae,
            r2 = r2,
            sampleSize = testFeatures.size
        )
    }

    private fun calculateMSE(actual: List<Double>, predicted: List<Double>): Double {
        return actual.zip(predicted).sumOf { (a, p) -> Math.pow(a - p, 2.0) } / actual.size
    }

    private fun calculateMAE(actual: List<Double>, predicted: List<Double>): Double {
        return actual.zip(predicted).sumOf { (a, p) -> kotlin.math.abs(a - p) } / actual.size
    }

    private fun calculateR2(actual: List<Double>, predicted: List<Double>): Double {
        val actualMean = actual.average()
        val ssRes = actual.zip(predicted).sumOf { (a, p) -> Math.pow(a - p, 2.0) }
        val ssTot = actual.sumOf { Math.pow(it - actualMean, 2.0) }

        return if (ssTot != 0.0) 1 - (ssRes / ssTot) else 0.0
    }

    /**
     * Gets training history
     */
    fun getTrainingHistory(): List<TrainingRecord> {
        return trainingHistory.toList()
    }

    /**
     * Gets available models
     */
    fun getAvailableModels(): List<String> {
        return models.keys.toList()
    }

    /**
     * Removes a model
     */
    fun removeModel(modelName: String): Boolean {
        return models.remove(modelName) != null
    }
}

// Machine learning data classes
interface MLModel {
    fun train(features: List<List<Double>>, targets: List<Double>): TrainingResult
    fun predict(features: List<Double>): Double
    fun getConfidence(): Double
}

class LinearRegressionModel : MLModel {
    private var coefficients: List<Double> = emptyList()
    private var intercept: Double = 0.0
    private var isTrained: Boolean = false
    private var confidence: Double = 0.0

    override fun train(features: List<List<Double>>, targets: List<Double>): TrainingResult {
        try {
            if (features.isEmpty() || targets.isEmpty()) {
                return TrainingResult(
                    success = false,
                    error = "Empty training data"
                )
            }

            val n = features.size
            val m = features.first().size

            // Simple gradient descent implementation
            val learningRate = 0.01
            val iterations = 1000

            var weights = (0..m).map { Random.nextDouble(-0.1, 0.1) }.toMutableList()

            repeat(iterations) {
                val predictions = features.map { feature ->
                    weights[0] + feature.zip(weights.drop(1)).sumOf { (x, w) -> x * w }
                }

                val errors = predictions.zip(targets).map { (pred, actual) -> pred - actual }

                // Update intercept
                weights[0] -= learningRate * errors.average()

                // Update feature weights
                for (j in 1..m) {
                    val gradient = features.mapIndexed { i, feature ->
                        errors[i] * feature[j - 1]
                    }.average()
                    weights[j] -= learningRate * gradient
                }
            }

            intercept = weights[0]
            coefficients = weights.drop(1)
            isTrained = true

            // Calculate confidence based on R²
            val predictions = features.map { predict(it) }
            val r2 = calculateR2(targets, predictions)
            confidence = kotlin.math.max(0.0, r2)

            return TrainingResult(
                success = true,
                metrics = mapOf(
                    "r2" to r2,
                    "iterations" to iterations,
                    "learningRate" to learningRate
                )
            )

        } catch (e: Exception) {
            return TrainingResult(
                success = false,
                error = e.message ?: "Training failed"
            )
        }
    }

    override fun predict(features: List<Double>): Double {
        if (!isTrained) {
            throw IllegalStateException("Model not trained")
        }

        if (features.size != coefficients.size) {
            throw IllegalArgumentException("Feature count mismatch")
        }

        return intercept + features.zip(coefficients).sumOf { (x, w) -> x * w }
    }

    override fun getConfidence(): Double {
        return confidence
    }

    private fun calculateR2(actual: List<Double>, predicted: List<Double>): Double {
        val actualMean = actual.average()
        val ssRes = actual.zip(predicted).sumOf { (a, p) -> Math.pow(a - p, 2.0) }
        val ssTot = actual.sumOf { Math.pow(it - actualMean, 2.0) }

        return if (ssTot != 0.0) 1 - (ssRes / ssTot) else 0.0
    }
}

data class TrainingResult(
    val success: Boolean,
    val error: String? = null,
    val modelName: String? = null,
    val trainingTime: Long = 0,
    val metrics: Map<String, Any> = emptyMap()
)

data class PredictionResult(
    val success: Boolean,
    val prediction: Double? = null,
    val confidence: Double = 0.0,
    val error: String? = null,
    val modelName: String? = null
)

data class ModelEvaluation(
    val success: Boolean,
    val mse: Double = 0.0,
    val rmse: Double = 0.0,
    val mae: Double = 0.0,
    val r2: Double = 0.0,
    val sampleSize: Int = 0,
    val error: String? = null
)

data class TrainingRecord(
    val modelName: String,
    val modelType: String,
    val trainingTime: Long,
    val success: Boolean,
    val error: String? = null,
    val timestamp: Long
)
