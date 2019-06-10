package org.openbravo.auction.agents.behaviours;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import jade.core.behaviours.Behaviour;

/**
 * 
 * @author Jhonny Vargas.
 *
 */

@SuppressWarnings("serial")
public class NewBuyerSubscriptionBehaviour extends Behaviour {

  private Date suscriptionPeriodDeadline;

  public NewBuyerSubscriptionBehaviour(Date suscriptionPeriodDeadline) {
    this.suscriptionPeriodDeadline = suscriptionPeriodDeadline;
  }

  @Override
  public void action() {
    System.out.println("Esperando suscripciones...");
  }

  /**
   * AQUÍ COMPROBAR SI LA SUBASTA YA HA EMPEZADO. EN ESE CASO, YA NO SE PUEDEN SUSCRIBIR MÁS
   * COMPRADORES Y SE COMPLETA EL COMPORTAMIENTO.
   * 
   */
  @Override
  public boolean done() {
    return DateUtils.isSameInstant(suscriptionPeriodDeadline, new Date());
  }

}
