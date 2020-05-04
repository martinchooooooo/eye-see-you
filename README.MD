# NSW Open Data Sample App

## App Architecture

### Observable Store

Inspired by some problems I've faced before and some articles I've read, I wanted a semi-generic solution to the fetch from network, cache in app and then read from cache thereafter. It's a common problem that I seem to solve in different way, but wanted to try somethign different here. Hopefully it's not overly complicated, but it only deals with caching network requests for now. The idea is that you provide a fetcher, which can be a function or a class that implements () -> Single<T>, and it will take care of making api calls and mapping it to some domain object where it will be cached in a BehaviourSubject internally to the Store. 

This is basically an implementation of the Repository pattern that's pretty common inside Android app's these day, however I'm yet to add local persistance to the mix. Though the idea of the current ObservableStore api (/repository pattern) is that making those database writes and read's should be seamless to consumers. 

I'm not sure how handy it is right now, but I've also included the previous cached values when performing a fetch or if there is an error, that way the UI can still show something if there previously was fetched data. 

`teardown()` There is an internal subscription inside of this store, which I never really like because it is something that could be leaked. In most cases however, you want the scope of the store (and data) to be for the duration of the Application lifecycle, so you may not be calling teardown() at all. However if you want to use an ObservableStore for data that may be scoped (e.g. storing network calls, with no db saves during a signup flow), then at the end of it you'll want to call `teardown()` which will complete any open subscribers downstream from the `get()` and also dispose of the internal subscribers. This should hopefully ensure there aren't any leaks and it can be GC'd.


### Reactive type

Since we don't want our streams to complete from the ObservableStore's, we need a data type that will encompass our data being emitted from stores in a Reactive way. It's meant to model the states that the data could be in, either Loading, Success or an Error, without completing the stream. 
