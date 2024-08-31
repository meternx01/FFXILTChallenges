package com.kd5kma.ffxiltchallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kd5kma.ffxiltchallenges.ui.theme.FFXILTChallengesTheme
import com.kd5kma.ffxiltchallenges.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FFXILTChallengesTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    SettingsPanel(viewModel)
                }) {

                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        CenterAlignedTopAppBar(
                            title = "Limited Time Challenges",
                            onNavigationIconClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            })
                    }) { innerPadding ->

//                        MainScreen(innerPadding, scope, drawerState,viewModel)

                        ChallengeTimer(viewModel, innerPadding)


                    }
                }

            }
        }
    }


}

//@Composable
//fun MainScreen(innerPadding: PaddingValues, scope: CoroutineScope, drawerState: DrawerState, viewModel: MainViewModel) {
//    ChallengeTimer(viewModel, innerPadding)
//    Button(onClick = {
//        scope.launch {
//            if (drawerState.isClosed) drawerState.open()
//            else drawerState.close()
//        }
//
//    }) { Text("Settings") }
//}

@Composable
fun SettingsPanel(viewModel: MainViewModel) {
    val isCatseye by viewModel.isCatseye.collectAsState()
    var buttonText by remember { mutableStateOf("Retail") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFF6C757D)) // bg-secondary
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings", style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White, fontWeight = FontWeight.ExtraBold
            ), textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Change Server",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            //modifier = Modifier.border(1.dp, Color(0xFF00BB00), RoundedCornerShape(8.dp))
        ) {
            Switch(checked = isCatseye, onCheckedChange = { isChecked ->
                buttonText = when (isChecked) {
                    true -> "Catseye"
                    false -> "Retail"
                }
                viewModel.setCatseye(isChecked)
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buttonText, style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White
                )
            )
        }
    }

}

@Composable
fun ChallengeTimer(viewModel: MainViewModel, innerPadding: PaddingValues) {
    val challenges by viewModel.challenges.collectAsState()
    val currentChallengeIndex = viewModel.getCurrentChallengeIndex()
    val currentChallenge = challenges.getOrNull(currentChallengeIndex)

    var timeUntilNextChallenge by remember { mutableStateOf(viewModel.getTimeUntilNextChallenge()) }

    LaunchedEffect(Unit) {
        while (true) {
            timeUntilNextChallenge = viewModel.getTimeUntilNextChallenge()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6C757D)) // bg-secondary
            .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                //.padding(16.dp)
                .border(2.dp, Color(0xFF00BB00)), color = Color(0xFF343A40), // bg-dark
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(
//                    text = "Limited Time Challenge",
//                    style = MaterialTheme.typography.headlineMedium.copy(
//                        color = Color.White, fontWeight = FontWeight.Bold
//                    ),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
                Text(
                    text = "Time left for", style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White,
                        //fontSize = 18.sp
                    ), modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                currentChallenge?.let { challenge ->
                    Text(
                        text = challenge.name, style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White, fontWeight = FontWeight.Bold
                        ), modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = challenge.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White, fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = timeUntilNextChallenge,
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = Color.White, fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(4.dp))

                val nextChallengeIndex = (currentChallengeIndex + 1) % challenges.size
                val nextChallenge = challenges[nextChallengeIndex]
                val localizedNextChallenge = viewModel.getLocalizedTime(nextChallenge.time)
                Text(
                    text = "Until: $localizedNextChallenge",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(3) { index ->
                val nextChallengeIndex = (currentChallengeIndex + index + 1) % challenges.size
                val nextChallenge = challenges[nextChallengeIndex]
                val challengeStartTime = viewModel.getFormatedTime(nextChallenge.time)

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(2.dp, Color(0xFF00BB00)), color = Color(0xFF343A40), // bg-dark
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = nextChallenge.name,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White, fontWeight = FontWeight.Bold
                            ),
//                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = challengeStartTime,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White, fontWeight = FontWeight.Light
                            ),
//                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                }
            }
        }
//
//        Text(
//            text = "Made by Meterman from Phoenix/Catseyexi 2024",
//            style = MaterialTheme.typography.bodySmall.copy(
//                color = Color.DarkGray, fontSize = 12.sp
//            ),
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBar(
    title: String,
    onNavigationIconClick: () -> Unit,
    backgroundColor: Color = Color(0xFF6C757D) // Your desired color
) {
    TopAppBar(title = {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
    }, navigationIcon = {
        IconButton(onClick = onNavigationIconClick) {
            Icon(
                imageVector = Icons.Filled.Menu, // Hamburger icon
                contentDescription = "Navigation Menu"
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = backgroundColor
    )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val fakeInnerPadding = PaddingValues(
        start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp
    )

    FFXILTChallengesTheme {
        ChallengeTimer(
            viewModel = MainViewModel(), innerPadding = fakeInnerPadding
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    FFXILTChallengesTheme {
        SettingsPanel(viewModel = MainViewModel())
    }
}