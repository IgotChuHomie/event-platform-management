package com.projecteventplatform.glcc.service;

import com.projecteventplatform.glcc.entities.Event;
import com.projecteventplatform.glcc.entities.User;

import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
    Event getEventById(Long id);
    List<Event> getAllEvents();
    List<Event> getEventsByOrganizer(User organizer);
    void addParticipantToEvent(Long eventId) ;
}