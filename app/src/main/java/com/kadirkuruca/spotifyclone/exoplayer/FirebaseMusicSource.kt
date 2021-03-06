package com.kadirkuruca.spotifyclone.exoplayer

class FirebaseMusicSource {

    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    private var state: State = State.STATE_CREATED
        set(value) {
            if(value == State.STATE_INITIAlIZED || value == State.STATE_ERROR){
                synchronized(onReadyListeners){
                    field = value
                    onReadyListeners.forEach{ listener ->
                        listener(state == State.STATE_INITIAlIZED)
                    }
                }
            }
            else{
                field = value
            }
        }

    fun whenReady(action: (Boolean) -> Unit): Boolean{
        if(state == State.STATE_CREATED || state == State.STATE_INITIALIZING){
            onReadyListeners += action
            return false
        }
        else{
            action(state == State.STATE_INITIAlIZED)
            return true
        }
    }
}

enum class State{
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIAlIZED,
    STATE_ERROR
}