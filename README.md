# Repo-Search-App
Application for performing simple repository searches via GitHub API

### Requirements:
- The app needs to implement GitHub repository search
- API call example: https://api.github.com/search/repositories?q=zagor
<br>

- Search results must be displayed as items in a list
- Each item in the list must have the following properties:
  - Repository name
  - Last update time
- The list must be sorted by the time of the latest update, the latest updated repository being first
- Clicking an item in the list must open a new screen and show additional repository details, containing the following properties:
  - Repository name
  - Last update time
  - Owner name
  - Description
- Some requirements, such as design and optimization, are omitted on purpose. They can be implemented at will

## Tech stack
- MVVM architecture
- Dagger Hilt
- LiveData
- Coroutines
- RecyclerView
- Retrofit
- Timber

## Lessons learned
1. **Think about what type of data the API returns**
   - When executing the API request, the app kept crashing, but there was no error show by Android Studio. After debugging, I found that Moshi was throwing an exception because of the *Description* field. It can sometimes be null in the response, if a user didn't set a description for their repository.
   - The problem was that I expected a non-nullable string in my model (the Repository data class). After changing the type to String?, the responses were delivered as expected.
   - I don't know why Moshi didn't propagate the exception so I could see it in the first place, but this taught me to double check what kind of data can actually be returned from an API.

2. **Think about which methods you override**
   - I used the onCreate method for creating a new activity, but overrode the implementation with arguments *savedInstanceState: Bundle?* and *persistentState: PersistableBundle?*.
   - When navigating to this new activity, the phone would show a blank screen, and no error was thrown. After a while of stumbling around in the dark, I realized that I overrode the wrong method. This in turn caused the activity to show none of its View elements.

3. **When a kaptXXX error message appears, just look at other parts of the message stack**
   - I was receiving a *kaptDebugKotlin* error message and lost a lot of time on StackOverflow trying to find what the problem was. In the end, I only needed to look at other messages in the message stack, which is where the actual error was mentioned.

4. **Some APIs are just badly described**
   - The GitHub API has a badly documented sort feature, [here](https://docs.github.com/en/free-pro-team@latest/github/searching-for-information-on-github/sorting-search-results#sort-by-updated-date). According to it, you have to append *" sort:updated"* to the repository name you are searching for (e.g. *q=zagor sort:updated*), but the problem is that it is impossible to add it when using Retrofit. 
   - Retrofit kept throwing MissingFormatArgumentExceptions, hinting that the repository name is of a wrong format. I then tried URL encoding the string myself using the *URLEncoder* class, and passing it again to Retrofit, but then it said that the % character was the problem.
   - In the end, I found that you could add sorting as a query parameter (*sort=updated*), which wasn't even mentioned on the official GitHub API website.
