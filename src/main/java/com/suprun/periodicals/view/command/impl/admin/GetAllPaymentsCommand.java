package com.suprun.periodicals.view.command.impl.admin;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.service.PaymentService;
import com.suprun.periodicals.service.ServiceException;
import com.suprun.periodicals.service.ServiceFactory;
import com.suprun.periodicals.view.command.Command;
import com.suprun.periodicals.view.command.CommandResult;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.constants.ViewsPath;
import com.suprun.periodicals.view.util.PageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for receiving all payments page.
 *
 * @author Andrei Suprun
 */
public class GetAllPaymentsCommand implements Command {
    private final PaymentService paymentService = ServiceFactory.getPaymentService();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PageManager paginationManager = new PageManager();
        List<Payment> payments;
        try {
            long rowsCount = paymentService.getPaymentsCount();
            long skip = paginationManager.manage(request, rowsCount);
            payments = paymentService.findAllPayments(skip, paginationManager.getRecordsPerPage());
        } catch (ServiceException e) {
            request.setAttribute(Attributes.SERVICE_EXCEPTION, e.getLocalizedMessage());
            return CommandResult.forward(ViewsPath.ERROR_GLOBAL_VIEW);
        }
        request.setAttribute(Attributes.PAYMENTS, payments);
        return CommandResult.forward(ViewsPath.PAYMENTS_VIEW);
    }
}
