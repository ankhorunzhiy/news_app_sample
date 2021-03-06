package com.sampleapp.domain.interactor;

import com.sampleapp.domain.data.executor.PostExecutionThread;
import com.sampleapp.domain.data.executor.WorkExecutionThread;
import com.sampleapp.domain.model.Event;
import com.sampleapp.domain.model.EventModel;
import com.sampleapp.domain.repository.ChatRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;


public class EventsConnectUseCase extends UseCase<EventModel, EventsConnectUseCase.Parameters>{

    private final ChatRepository chatRepository;
    @Inject
    protected EventsConnectUseCase(ChatRepository chatRepository, WorkExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.chatRepository = chatRepository;
    }

    @Override
    protected Flowable<EventModel> buildUseCaseObservable(Parameters params) {
        return chatRepository.on(params.events);
    }

    public static class Parameters{

        private final Event[] events;

        public Parameters(Event[] events){
            this.events = events;
        }

        public Event[] getEvents() {
            return events;
        }
    }

    public static EventsConnectUseCase mock(ChatRepository chatRepository, WorkExecutionThread threadExecutor, PostExecutionThread postExecutionThread){
        return new EventsConnectUseCase(chatRepository, threadExecutor, postExecutionThread);
    }
}
