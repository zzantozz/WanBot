package org.wanbot

import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.junit.runner.RunWith
import org.junit.Ignore
import org.junit.Test
import static org.junit.Assert.*

@RunWith(Parameterized.class)
class NegativeFindPointAssignmentsTest {
    def bot = new WanBot()
    
    def text
    def expectedAssignments
    
    NegativeFindPointAssignmentsTest(text, String... expectedAssignments) {
        this.text = text
        this.expectedAssignments = expectedAssignments
    }
    
    @Parameters
    static data() {
    [
        // Negative cases
        [ 'horky -7 points', [] as String[] ] as Object[],
        [ '-10 mike', [] as String[] ] as Object[],
        [ '8pts', [] as String[] ] as Object[],
        [ '18pits franklin', [] as String[] ] as Object[],
        [ 'af3', [] as String[] ] as Object[],
        [ 'john:', [] as String[] ] as Object[],
        [ 'horky is 5 years old', [] as String[] ] as Object[],
        [ 'i like artificial coloring especially yellow 5', [] as String[] ] as Object[],
        [ 'frank', [] as String[] ] as Object[],
        [ '11', [] as String[] ] as Object[],
        [ '>Mike +10', [] as String[] ] as Object[],
        [ '> +10 mike', [] as String[] ] as Object[],
        [ '> 3 pts for mike', [] as String[] ] as Object[]
    ]
    }
    
    @Test
    void parameterized_find_assignments() {
        def assignments = bot.findPointAssignmentsInMessageText(text)
        assertEquals "Number of assignments for text '${text}': ", 0, assignments.size()
    }
}