enum class IntOperationType {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

typealias IntOperation = (Int, Int) -> Int

typealias IntOperationMap = Map<IntOperationType, IntOperation>

//  Task 0
fun task0(): IntOperationMap {
    return mapOf(IntOperationType.ADD to { a, b -> a + b },
        IntOperationType.SUBTRACT to { a, b -> a - b },
        IntOperationType.MULTIPLY to { a, b -> a * b },
        IntOperationType.DIVIDE to { a, b -> a / b })
}

typealias IntResult = () -> Int

//  Task 1
fun task1(a: Int, b: Int): IntResult? {
    return { a + b }
}

//  Task 2
fun task2(execute: () -> Int): IntResult? {
    return { execute() }
}

//  Task 3
fun task3(op: IntOperationType, first: () -> Int, second: () -> Int): IntResult? {
    if (op == IntOperationType.ADD) {
        return { first() + second() }
    }

    if (op == IntOperationType.SUBTRACT) {
        return { first() - second() }
    }

    if (op == IntOperationType.MULTIPLY) {
        return { first() * second() }
    }

    return { first() / second() }
}

class AnyValue {

    private val value: Any

    constructor(s: String) { value = s }
    constructor(i: Int) { value = i }

    fun stringValue(): String? { return value as? String  }
    fun intValue(): Int? { return value as? Int }

    val isString: Boolean
        get() = stringValue() != null

    val isInt: Boolean
        get() = intValue() != null

    fun canPerformOperation(other: AnyValue, type: OperationType): Boolean {

        // If the operation is ADD or SUBTRACT, only number types can be added together
        if (type == OperationType.ADD || type == OperationType.SUBTRACT) {
            return isInt && other.isInt
        }

        if (type == OperationType.CONCATENATE) {
            return isString && other.isString
        }

        return false
    }

    fun add(other: AnyValue): Pair<OperationStatus, AnyValue?> {
        if (!canPerformOperation(other, OperationType.ADD)) {
            return Pair(OperationStatus.INVALID, null)
        }

        val result = (intValue() ?: 0) + (other.intValue() ?: 0);
        return Pair(OperationStatus.VALID, AnyValue(result))
    }

    fun subtract(other: AnyValue): Pair<OperationStatus, AnyValue?> {
        if (!canPerformOperation(other, OperationType.SUBTRACT)) {
            return Pair(OperationStatus.INVALID, null)
        }

        val result = (intValue() ?: 0) - (other.intValue() ?: 0);
        return Pair(OperationStatus.VALID, AnyValue(result))
    }

    fun concat(other: AnyValue): Pair<OperationStatus, AnyValue?> {
        if (!canPerformOperation(other, OperationType.CONCATENATE)) {
            return Pair(OperationStatus.INVALID, null)
        }

        val result = stringValue() + other.stringValue()
        return Pair(OperationStatus.VALID, AnyValue(result))
    }
}

enum class OperationType {
    ADD, SUBTRACT, CONCATENATE
}

enum class OperationStatus {
    VALID, INVALID
}

// Was struggling with task 4, this article helped, I figured I should include it as
// it helped me solve this problem.
// https://www.baeldung.com/kotlin/lambda-expressions
//  Task 4
typealias OperationHandler = (OperationType, AnyValue, AnyValue) -> Pair<OperationStatus, AnyValue?>
fun task4(): OperationHandler? {

    return { op: OperationType, a: AnyValue, b: AnyValue ->

        when(op) {
            OperationType.ADD -> a.add(b)
            OperationType.SUBTRACT -> a.subtract(b)
            OperationType.CONCATENATE -> a.concat(b)
        }
    }
}