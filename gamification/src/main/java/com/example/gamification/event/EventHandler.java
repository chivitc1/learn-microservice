package com.example.gamification.event;

import com.example.gamification.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Slf4j
@Component
public class EventHandler
{
	private GameService gameService;

	EventHandler(final GameService gameService) {
		this.gameService = gameService;
	}

	@RabbitListener(queues = "${multiplication.queue}")
	void handleMultiplicationSolved(final MultiplicationSolvedEvent _solvedEvent) {
		log.info("Multiplication Solved Event received: {}",
				_solvedEvent.getMultiplicationResultAttemptId());
		try {
			gameService.newAttemptForUser(_solvedEvent.getUserId(),
					_solvedEvent.getMultiplicationResultAttemptId(),
					_solvedEvent.isCorrect());
		} catch (final Exception _e) {
			log.error("Error when trying to process MultiplicationSolvedEvent: ", _e);

			// Avoids the event to be re-queued and reprocessed.
			throw new AmqpRejectAndDontRequeueException(_e);
		}
	}
}
