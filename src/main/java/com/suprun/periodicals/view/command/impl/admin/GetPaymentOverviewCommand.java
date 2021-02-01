package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.service.PaymentService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.service.SubscriptionService;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.RequestParameters;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Command for receiving payment overview page.
 *
 * @author Andrei Suprun
 */
public class GetPaymentOverviewCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPaymentOverviewCommand.class);
    private final PaymentService paymentService = ServiceFactory.getPaymentService();
    private final SubscriptionService subscriptionService = ServiceFactory.getSubscriptionService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Attempt to get a payment overview page");
        Long paymentId = Long.valueOf(request.getParameter(RequestParameters.PAYMENT_ID));
        Optional<Payment> paymentOpt;
        try {
            paymentOpt = paymentService.findPaymentById(paymentId);
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            List<Subscription> subscription;
            try {
                subscription = subscriptionService.findAllSubscriptionsByPayment(payment);
            } catch (ServiceException e) {
                request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
                return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
            }
            request.setAttribute(Attributes.PAYMENT_DTO, payment);
            request.setAttribute(Attributes.SUBSCRIPTIONS, subscription);
            LOGGER.debug("Attempt to get a payment overview page is successful");
            return CommandResult.forward(ViewsPath.PAYMENT_OVERVIEW_VIEW);
        }
        LOGGER.debug("Payment with id {} doesn't exist", paymentId);
        throw new NotFoundException();
    }
}
