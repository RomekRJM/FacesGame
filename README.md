# FacesGame
Android game, where you have to match photo of a person with nationality.

## I want to play!

Check it at Google Play!


## Special thanks to:

1. Wikimedia Commons, for beeing great source of free images. All photos in game are CC / public domain licensed and downloaded from wikipedia.org:
    http://romekrjm.github.io/FacesGame/
2. http://artdesigncat.com/ - for creating great blue faces icons and free 64x64 set, that I used for achievements.
3. http://sekkyumu.deviantart.com/ - for additional icons.
4. https://www.iconfinder.com/konekierto - for check mark/cross mark icons.
5. http://www.icojam.com/ - for main game icon.
6. Creator and all contributors of https://github.com/daimajia/AndroidViewAnimations library, for their awesome work.
Game uses it to animate scores.
7. My wife Sabina, for understanding and support :D


## Changes history

**v1.05 - 30.07.2015**

* First version officially published on Google Play Store!!!
* Removed or replaced some pictures and prepared licensing document for Google.
* Added back link to license file in app about.


**v1.0 - 02.07.2015**

Enhancements:

* 3x more pictures.
* Rebalanced achievements (yet again, as logic has changed and game was to easy).

Bug fixes:

* Redone achievements screen, to fix clicking issues on some older devices.
* Separate layout for main menu in landscape mode, to make it look better.
* Wrong numbers displayed in Scores, for phones with smaller screen.
* Game is paused, when not active.

**v0.8 - 15.06.2015**

Enhancements:

* Redesigned question generation, to make game easier. Added extra difficulty level. First three levels will display countries from different continents. Last two will get country from given radius, around correct country.
* Rebalanced achievements (again): it is now impossible to unlock 2 achievements from the same group in one game.
* Added difficulty bar above portrait.
* Display text, when no achievements unlocked.
* After last question game automatically goes to end game screen. No need to press the button.

Bug fixes:

* Wrong player name on top scores.
* Error when saving timed out question in db.

**v0.7 - 07.06.2015**

Enhancements:

* Connected in game achievements to Google Play API achievements.
* Set up the project and api in Google Play.
* Added new achievements: guess n people from country x, play game n days in a row, guess n people, you haven't guessed previously, reach difficulty x.
* Rebalanced achievements.

**v0.6 - 24.05.2015**

Enhancements:

* Google Game API integration.
* Leaderboards support: player scores are now saved in google cloud and matched against others from around the world.
* Redesigned main menu screen, to be more eye-catchy and show game version.
* Added about screen, displaying credits and backlinks.
* Dedicated game icon.

**v0.5 - 06.05.2015**

Enhancements:

* Added lives. Game is over, when 3 wrong answers given.
* Question generation is now done on separate thread, one by one upon answering, instead of
generating all at the beginning. This results in 7x shorter waiting time, when game loads.
* Prettified scores. They are now animated upon change.
Different animation for correct and wrong answer.
* Loading screen appears after choosing New Game.
* The same person will not be shown again in single game.

**v0.4 - 22.04.2015**

Enhancements:

* Added achievements subsystem backed up by sqlite.
* New view: Collectable, for displaying detailed info about achievements, using funny faces.
* Implemented logic for following achievements: "Guess x in a row", "Have total x correct guesses",
"Complete x game", "Reach score x", "Gather x points in any number of games"
* Achievements shown on end game screen.

**v0.3 - 04.04.2015**

Enhancements:

* Added missing countries and photos: Abkhazia, Somaliland, Kosovo, Vatican City, 
  Transnistria, South Ossetia, Nagorno-Karabakh, Maldives, Saint Lucia, Norfolk Island.
* Implemented storage of scores in sqlite and top scores view.
* Difficulty level grows every few question: less time, more possible answers, 
  countries closer to each other.
  
Bug fixes:

* End game buttons now visible on landscape mode.
* Corrected behaviour of faces_algorithm library, for randomizing close countries.  


**v0.2 - 25.03.2015**

Enhancements:

* Implemented timeout, marked by timer bar, that smoothly changes colors, from green to red, when time passes.
* More sophisticated method, for computing and displaying score, based on how quick answer was given and answering spree multiplier.
* Portrait flashes changing color to red/green with check/cross mark, when correct/wrong answer given.
* User has to press a button to go to next question.


**v0.1 - 16.03.2015**

* Initial, somehow playable version.
