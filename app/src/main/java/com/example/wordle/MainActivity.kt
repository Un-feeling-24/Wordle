package com.example.wordle

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordle.ui.theme.WordleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   WordleApp()
                }
            }
        }
    }
}

@Composable
fun WordleApp(){
    val fourletterwordlist = FourLetterWordList() // getting access to FourLetterWordList Class

    val wordToGuess = fourletterwordlist.getRandomFourLetterWord() // assigning  4 letter word to guess

    Column(verticalArrangement = Arrangement.Bottom)

    {//repeat columns if button pressed but change column after button pressed

        // TODO: Use this to show guesss check
        GuessTextField(modifier = Modifier.weight(1f), wordToGuess)
    }

}


@Composable
private fun GuessTextField (modifier: Modifier, wordToGuess: String)
{
    var isVisible by remember { mutableStateOf( false ) }
    var show  by remember { mutableStateOf( false ) }
    var word by remember {mutableStateOf(" ")}
    var text by remember {mutableStateOf(" ")}
    var count  by remember { mutableIntStateOf(0) }
    var columnCount  by remember { mutableIntStateOf(0) }



    Column( modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom) {

        Text(text = wordToGuess)

        if (show) {// checks whether or not button is visible
            isVisible = false
            if (columnCount <= 3){
                repeat(columnCount) {
                    ColumnGuess(modifier = Modifier.weight(1f), count = count, guess = word, check = wordToGuess)
                    if (columnCount == 3){ columnCount = 0 }
                }
                ShowGuess(modifier = Modifier, guess = word)

            }
        }


        Row {
            TextField(
                value = text,
                onValueChange = { newValue ->
                    text = newValue },
                label = { Text("Enter 4 Letter Guess here:") }
            )
            Button(onClick = {
                columnCount+=1

                isVisible = true
                show = isVisible
                word = text
                count = columnCount
            }) {
                Text(text = "Submit")
            }
        }

    }
}
@Composable
fun ColumnGuess(modifier: Modifier, guess: String, count : Int, check : String){

    Column()
    {

        Row {
            Text("Guess Check $count")
            Spacer(modifier.weight(1f))
            Text(text = checkGuess(guess, check))
        }
    }

    }
@Composable
fun ShowGuess (modifier: Modifier, guess: String){
    Text(text = guess  )
}

@Composable
fun checkGuess(guess: String, wordToGuess : String) : String {

    var result = " "

    if (guess.length < 4) {
        return " "
    }

    for (i in 0..3) {
        if (guess[i] == wordToGuess[i]) {
            result += "O"
        }
        else if (guess[i] in wordToGuess) {
            result += "+"
        }
        else {
            result += "X"
        }
    }
    return result

}


// get input
@Preview(showBackground = true)
@Composable
fun WordlePreview() {
    WordleTheme {
        WordleApp()
    }
}