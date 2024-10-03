data class Word(val value: String)
data class Context(val name: String)
data class Translate(val value: String)

typealias ContextMap = MutableMap<Context, MutableList<Translate>>

class Translator {
    private val dictionary: MutableMap<Word, ContextMap> = mutableMapOf()

    fun add(word: Word, context: Context, translate: Translate) {
        val contextMap = dictionary.getOrPut(word) { mutableMapOf() }
        val translations = contextMap.getOrPut(context) { mutableListOf() }
        translations.add(translate)
    }

    fun remove(word: Word, context: Context, translate: Translate) {
        dictionary[word]?.let { contextMap ->
            if (context !in contextMap) {
                println("Контекст «${context.name}» не найден для слова «${word.value}»!")
                return
            }

            if (contextMap[context]?.contains(translate) == true) {
                contextMap[context]?.remove(translate)
            }
            else {
                println("Перевод для слова ${word.value}» в контексте «${context.name}» не найден в словаре!")
                return
            }

            if (contextMap[context]?.isEmpty() == true) {
                contextMap.remove(context)
            }

            if (contextMap.isEmpty()) {
                dictionary.remove(word)
            }

            println("Удалено!")
        } ?: println("Слово «${word.value}» не найдено в словаре!")
    }

    fun getTranslate(word: Word): Map<Context, MutableList<Translate>> {
        return dictionary[word] ?: emptyMap()
    }

    fun words(): Map<Word, ContextMap> {
        return dictionary.toMap()
    }
}

fun main() {
    val translator = Translator()

    while (true) {
        print("Введите команду: ")
        val input = readLine() ?: break
        val parts = input.split(" ")

        when (parts[0]) {
            "add" -> {
                if (parts.size == 4) {
                    translator.add(Word(parts[1]), Context(parts[2]), Translate(parts[3]))
                    println("Добавлено!")
                } else {
                    println("Неверный формат команды.")
                }
            }
            "remove" -> {
                if (parts.size == 4) {
                    translator.remove(Word(parts[1]), Context(parts[2]), Translate(parts[3]))
                } else {
                    println("Неверный формат команды.")
                }
            }
            "translate" -> {
                if (parts.size == 2) {
                    val translations = translator.getTranslate(Word(parts[1]))
                    if (translations.isEmpty()) {
                        println("Переводов не найдено.")
                    } else {
                        println("Перевод слова «${parts[1]}»:")
                        for ((context, translates) in translations) {
                            println("В контексте «${context.name}»: ${translates.joinToString(", ") { it.value }}")
                        }
                    }
                } else {
                    println("Неверный формат команды.")
                }
            }
            "print" -> {
                val allWords = translator.words()
                for ((word, contexts) in allWords) {
                    println("Перевод слова «${word.value}»:")
                    for ((context, translates) in contexts) {
                        println("В контексте «${context.name}»: ${translates.joinToString(", ") { it.value }}")
                    }
                }
            }
            "exit" -> break
            else -> println("Неизвестная команда.")
        }
    }
}
