# Java starter for advent of code

Java code to solve 2024 puzzles for the Advent of Code https://adventofcode.com/
Based on setup code from Zebalu https://github.com/zebalu/advent-of-code-2023

## How to use

### Setup

Make sure the resources folder exists (under src/main/). This will be used to store all inputs.

In order to be able to use the automatic input download, you will need to set up 2 environment variables:

1. AOC_YEAR
2. AOC_SESSION_ID

AOC_YEAR is the year you want to download all input from, in this case it should be set to 2024.
AOC_SESSION_ID is your cookie session which is used to get your specific input files. You can find this by opening
the dev tools network tab whole logged in on the advent of code website. In the request headers of the page load,
you will see a Cookie header. In this header you will see a value set for the 'session'.

### Run

Multiple gradle tasks will be generated. Under 'aoc util' you will find a task to download all inputs. Run this every
day to get the latest input, and a task to run all days which have been added to App.java. When running all days, it
will also time the runtime of every part. Every time you call `System.out.println()` it will start the timer of a
new part. Every time you call `System.out.println()` it will start the timer of a new part.

Under 'aoc day' you will find a task to run each day individually.