package com.abnamro.core.base.model

/**
 * A generic interface for mapping an input of type [INPUT] to an output of type [OUTPUT].
 *
 * This interface should be implemented by any class that aims to transform data from one type to another.
 *
 * @property INPUT the type of the input object that will be mapped.
 * @property OUTPUT the type of the output object that will be produced.
 */
interface DataMapper<INPUT, OUTPUT> {
    /**
     * Maps an input object of type [INPUT] to an output object of type [OUTPUT].
     *
     * @param input the input object to be mapped.
     * @return the mapped output object.
     */
    fun map(input: INPUT): OUTPUT
}