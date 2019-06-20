package org.openbravo.auction.agents.behaviours;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class NewBuyerSubscriptionBehaviour extends Behaviour {

  private Date celebrationDate;
  private MessageTemplate messageTemplate;

  public NewBuyerSubscriptionBehaviour(Date celebrationDate) {
    this.celebrationDate = celebrationDate;

    messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
  }

  @Override
  public void action() {
    ACLMessage msg = myAgent.receive(messageTemplate);

    if (msg != null) {
      System.out.println("Se va a registrar el comprador con email " + msg);
    }

    // System.out.println("Esperando suscripciones...");
  }

  /**
   * Checks if the auction has already begun.
   */
  @Override
  public boolean done() {
    return DateUtils.isSameInstant(celebrationDate, new Date());
  }

  @Override
  public int onEnd() {
    myAgent.doDelete();
    return 0;
  }

}
