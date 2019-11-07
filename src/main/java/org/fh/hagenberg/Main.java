package org.fh.hagenberg;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {
  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("akka_PingPong");
    ActorRef pingActor = system.actorOf(Ping.props(), "pingActor");
    pingActor.tell("init", null);

    system.getWhenTerminated();
  }
}
