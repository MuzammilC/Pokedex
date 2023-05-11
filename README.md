# Pokédex

## The perfect companion for your Pokémon journey.

This application will help users create their Pokémon rosters and provide information of each of those
Pokémon such as their type, experience level, health level, special moves, weaknesses, and their stats.
It will also indicate whether we have caught a certain Pokémon and consqeuently, if we can add a Pokémon
to our Roster. Aditionally, it will also store information about other Pokémon that we may not have 
caught as of yet.

This application will be useful to those who play the Pokémon games
and the _Pokémon Trading Card Game_ to strategize while making their rosters
such that its efficient according to their needs and playing style. 

I'm interested in making this project because I have been obsessed with Pokémon,
ever since I was a kid. I've played a lot of Pokémon games, but I've never found the
in-built Pokedex to be quite useful in terms of strategizing my roster to win battles in game. 

User Stories Phase 2/3:
- As a user, I want to be able to catch a Pokémon
- As a user, I want to be able to add a Pokémon that I've caught to my roster.
- As a user, I want to be able to retrieve information about Pokémon from my roster.
- As a user, I want to be able to remove a Pokémon from my roster.
- As a user, I want to be able to change the moves of a Pokemon in my Roster.
- As a user, I want to bea ble to save my Pokemon Roster.
- As a user, I want to be able to load my previous Pokemon roster.
- As a user, I want to be able to save changes to the Pokemon database.
- As a user, I want to be able to load the most current Pokemon database.

TA grading instructions:
- You can locate my visual component as it is a splash screen that pops up before the program starts
- You can generate the first required action related to adding Xs to a Y by clicking on the start button, then clicking on the information about roster button and then clicking on info pokemon button. 
- You can generate the second required action related to adding Xs to a Y by clicking on the start button, then clicking on the information about roster button and then clicking on Show caught Pokemon button.
- You can generate the another action related to adding Xs to a Y by clicking on the start button, then clicking on the information about roster button and then clicking on add Pokemon button. (Note: only pokemon which are caught can be added to the roster)
- You can save the state of my application by clicking on the start button, then clicking on the information about database button and make changes in the database prompt and then exiting that window.
- You can reload the state of my application by clicking on the start button, then clicking on the information about database button which will prompt you to use the previous saved database.

Phase 4: Task 2
- Tue Apr 04 16:29:15 PDT 2023 
- Added Fletchling to the Database
- Tue Apr 04 16:29:19 PDT 2023
- Caught PokemonFletchling
- Tue Apr 04 16:29:35 PDT 2023
- Added  Fletchling to the roster!!
- Tue Apr 04 16:29:39 PDT 2023
- Removed Raichu from the roster!!

Phase 4: Task 3
If I had some time to refactor I would do the follwing things:
- Make the CollectionOfPokemon class implement Iterable. This would help in making the CollectOfPokemon class and its subclasses easy to iterate 
over without knowing too much about what the class contains and how it functions. It would also help to remove some of the redudndant functions that I was forced to create in the GUI classes because I couldnt not iterate over a subclass of the CollectionOfPokemonClass. 
- Make an abstract class which would be extended by the DatabaseGUI and RosterGUI class. This would help me delegate some of the common functions and inner classes to this abstract class. This would help in maintaining the readability of the program which handles the GUI. 


