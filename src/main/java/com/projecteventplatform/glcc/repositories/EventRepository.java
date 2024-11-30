package com.projecteventplatform.glcc.repositories;

import com.projecteventplatform.glcc.entities.Event;
import com.projecteventplatform.glcc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizer(User Organizer) ;
}