package org.fh.hagenberg;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Pong extends AbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  static Props props() {
    return Props.create(Pong.class);
  }

  private void handleMsg(String msg) {
    log.info("In PongActor - received message: {}", msg);
    getSender().tell("pong", getSelf());
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(String.class, this::handleMsg)
        .build();
  }
}
