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

## Application consists of
* MVVM architecture
* Dagger Hilt
* LiveData
* Coroutines
* RecyclerView
* Retrofit
* Timber
