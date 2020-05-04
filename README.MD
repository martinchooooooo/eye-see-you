# NSW Open Data Sample App

A sample Android app built to try out some ideas and architecture patterns that I've thought about, read about or want to see if it can solve some problems I've faced in previous projects. 

I thought it would also be cool and interesting to showcase some of the data that is currently available as part of NSW open data scheme here [https://api.nsw.gov.au/](https://api.nsw.gov.au/) 


## Setup

Most of it is stock standard Andorid setup that Android Studio should be able to handle for you, the only thing is that you'll need an API key in order to make the network calls. You can register for one [here for the TransportNSW data](https://opendata.transport.nsw.gov.au/) and include it in the `cravingsModule.kt` in the module for the LibTransport


## TODO

- [ ] Implement local persistance in our ObservableStore's
- [ ] Add a ‘Debug’ menu button in the app so that we can switch between stubs and a ‘real’ env
- [ ] Make it 'pop'


## App Architecture

I try to use the Jetpack recommended architecture along with good 'ol Uncle Bob's Clean Architecture with some of my own twists that I think are handy. I like to put all my domain logic in a separate pur kotlin/java gradle module with no Android dependencies. That way it can compile quicker and when working in a team it stops anyone accidently using Andoroid framework stuff that may require an instrumented test. This also means that any business rules are usually put in here, all behind a `Lib*` interface that we can mock in our presentation layer for straightforward testing or mocking if we want to stub data out while building something.

### View Binding

I haven't used this before, but definitely familiar with findViewById(), so it seems pretty straightforward. I quite like data binding and find it quite 'cute' (dare I say it) and really nice for expressing a one-way data flow through the app. However in larger projects I've worked on, compilation times can get quite large and I've tried to find ways to reduce it. If using view bindings (a.k.a not having `kapt`) can help with this, then I reckon I could sacrifice the cuteness of databinding and have a `bind()` method in my activites and fragments for binding all the data.

I would stil have the idea of customer Binding adapter, however they would more likely be util static functions that I can call and pass through args as needed.

### DI

Dagger, what a beat eh? To be honest, I quite like Dagger and feel I've gone through enough pain to solve my issues with it, however as with databinding, my goal in this is to reduce potential build times that can grow in larger projects. My thinking is that if most of my business rules are in the `Lib*` packages, where I would manually handle DI, then my need for it in the UI layer would be much less. (i.e. just use androidx.activity and androidx.fragment with their `by viewModel()`'s). However, as soon as I wanted to start adding `SavedStateHandle`'s to my view models, then it meant I would start having to add Factories for each VM in order to use them in my app. This I didn't want to do. 

I had had some previous exposure to [Koin](https://insert-koin.io/) and reading the docs found that it can handle SavedStateHandle, as well as other custom args to view models quite nicely insdie their modules. I chose the ease of setting that up vs writting custom factories for each view model as they'd be shorter and quicker to write. Also I quite like using scope's when appropriate and it seems their scopes stuff is more mature than last time I looked at it.


### Observable Store

Inspired by some problems I've faced before and some articles I've read, I wanted a semi-generic solution to the fetch from network, cache in app and then read from cache thereafter. It's a common problem that I seem to solve in different way, but wanted to try somethign different here. Hopefully it's not overly complicated, but it only deals with caching network requests for now. The idea is that you provide a fetcher, which can be a function or a class that implements () -> Single<T>, and it will take care of making api calls and mapping it to some domain object where it will be cached in a BehaviourSubject internally to the Store. 

This is basically an implementation of the Repository pattern that's pretty common inside Android app's these day, however I'm yet to add local persistance to the mix. Though the idea of the current ObservableStore api (/repository pattern) is that making those database writes and read's should be seamless to consumers. 

I'm not sure how handy it is right now, but I've also included the previous cached values when performing a fetch or if there is an error, that way the UI can still show something if there previously was fetched data. 

`teardown()` There is an internal subscription inside of this store, which I never really like because it is something that could be leaked. In most cases however, you want the scope of the store (and data) to be for the duration of the Application lifecycle, so you may not be calling teardown() at all. However if you want to use an ObservableStore for data that may be scoped (e.g. storing network calls, with no db saves during a signup flow), then at the end of it you'll want to call `teardown()` which will complete any open subscribers downstream from the `get()` and also dispose of the internal subscribers. This should hopefully ensure there aren't any leaks and it can be GC'd.


### Reactive type

Since we don't want our streams to complete from the ObservableStore's, we need a data type that will encompass our data being emitted from stores in a Reactive way. It's meant to model the states that the data could be in, either Loading, Success or an Error, without completing the stream. 
