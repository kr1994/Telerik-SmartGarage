package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.*;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.viewDto.CustomerViewDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;


    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User order by userId",
                    User.class);
            return query.list();
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", "id", id);
            }
            return user;
        }
    }

    @Override
    public List<User> getByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u join PersonalInfo p " +
                            "where u.personalInfo.firstName = :firstName order by u.id",
                    User.class);
            query.setParameter("firstName", firstName);
            return query.list();
        }
    }

    @Override
    public List<User> getByLastName(String lastName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u join PersonalInfo p " +
                            "where u.personalInfo.lastName = :lastName order by u.id",
                    User.class);
            query.setParameter("lastName", lastName);
            return query.list();
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User u join PersonalInfo p " +
                            "where u.personalInfo.email = :email order by u.id",
                    User.class);
            query.setParameter("email", email);
            return query.list().get(0);
        }
    }

    @Override
    public User create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }

        return user;
    }

    @Override
    public User update(User user,
                       Credential credential,
                       PersonalInfo personalInfo,
                       UserType userType) {

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            userUpdate(user, session, credential, personalInfo, userType);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return user;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    private void userUpdate(User user,
                            Session session,
                            Credential credential,
                            PersonalInfo personalInfo,
                            UserType userType) {

        user.setCredential(credential);
        user.setPersonalInfo(personalInfo);
        user.setUserType(userType);
        session.update(credential);
        session.update(personalInfo);
        session.update(userType);
        session.update(user);
    }

    @Override
    public List<CustomerViewDto> filterCustomers(Optional<String> firstName,
                                                 Optional<String> lastName,
                                                 Optional<String> email,
                                                 Optional<String> phoneNumber,
                                                 Optional<String> model,
                                                 Optional<Date> dateFrom,
                                                 Optional<Date> dateTo) {

        try (Session session = sessionFactory.openSession()) {

            List<CustomerViewDto> result = new ArrayList<CustomerViewDto>();
            int countParameters = 0;
            String queryStr = "from CarService cs ";

            if (firstName.isPresent()) {
                queryStr += "where automobile.user.personalInfo.firstName = :firstName ";
                countParameters++;
            }

            if (lastName.isPresent()) {
                if (countParameters > 0) {
                    queryStr += "and automobile.user.personalInfo.lastName = :lastName ";
                } else {
                    queryStr += "where automobile.user.personalInfo.lastName = :lastName ";
                }
                countParameters++;
            }

            if (email.isPresent()) {
                if (countParameters > 0) {
                    queryStr += "and automobile.user.personalInfo.email = :email ";
                } else {
                    queryStr += "where automobile.user.personalInfo.email = :email ";
                }
                countParameters++;
            }

            if (phoneNumber.isPresent()) {
                if (countParameters > 0) {
                    queryStr += "and automobile.user.personalInfo.phoneNumber = :phoneNumber ";
                } else {
                    queryStr += "where automobile.user.personalInfo.phoneNumber = :phoneNumber ";
                }
                countParameters++;
            }

            if (model.isPresent()) {
                if (countParameters > 0) {
                    queryStr += "and automobile.model.modelName = :model";
                } else {
                    queryStr += "where automobile.model.modelName = :model";
                }
                countParameters++;
            }

            if (dateFrom.isPresent() && dateTo.isPresent()) {
                if (countParameters > 0) {
                    queryStr += "and invoice.date >= :dateStart and invoice.date <= :dateEnd";
                } else {
                    queryStr += "where invoice.date >= :dateStart and invoice.date <= :dateEnd";
                }
                countParameters++;
            }

            Query<CarService> queryForVisits;

            if (countParameters > 0) {
                queryStr += " and automobile.user.userType.type = :customer";
                queryForVisits = session.createQuery(queryStr, CarService.class);

                if (firstName.isPresent()) {
                    queryForVisits.setParameter("firstName", firstName.get());
                }

                if (lastName.isPresent()) {
                    queryForVisits.setParameter("lastName", lastName.get());
                }

                if (email.isPresent()) {
                    queryForVisits.setParameter("email", email.get());
                }

                if (phoneNumber.isPresent()) {
                    queryForVisits.setParameter("phoneNumber", phoneNumber.get());
                }

                if (model.isPresent()) {
                    queryForVisits.setParameter("model", model.get());
                }

                if (dateFrom.isPresent() && dateTo.isPresent()) {
                    Date dateStart = dateFrom.get();
                    Date dateEnd = dateTo.get();
                    queryForVisits.setParameter("dateStart", dateStart);
                    queryForVisits.setParameter("dateEnd", dateEnd);
                }

                queryForVisits.setParameter("customer", "Customer");

            } else {
                queryStr += "where automobile.user.userType.type = :customer";
                queryForVisits = session.createQuery(queryStr, CarService.class);
                queryForVisits.setParameter("customer", "Customer");
            }


            for (CarService cs : queryForVisits.getResultList()) {
                CustomerViewDto cvd = new CustomerViewDto();
                List<Date> currentVisits = new ArrayList<Date>();
                cvd.setFirstName(cs.getCar().getOwner().getPersonalInfo().getFirstName());
                cvd.setLastName(cs.getCar().getOwner().getPersonalInfo().getLastName());
                cvd.setEmail(cs.getCar().getOwner().getPersonalInfo().getEmail());
                cvd.setPhoneNumber(cs.getCar().getOwner().getPersonalInfo().getPhoneNumber());
                cvd.setUserType(cs.getCar().getOwner().getUserType());
                cvd.setCarModel(cs.getCar().getModel().getModelName());
                currentVisits.add(cs.getInvoice().getDate());
                cvd.setVisitsInRange(currentVisits);
                result.add(cvd);
            }

            List<Integer> indexesForDeletion = new ArrayList<Integer>();
            int resultTempSize = result.size();
            List<Date> currentVisits = new ArrayList<Date>();
            List<CustomerViewDto> newResult = new ArrayList<CustomerViewDto>();
            int duplicateCounter = 0;
            if (resultTempSize > 1) {
                for (int i = 0; i < resultTempSize - 1; i++) {
                    CustomerViewDto cvd = result.get(i);
                    CustomerViewDto cvdNext = result.get(i + 1);
                    if (duplicateCounter == 0) {
                        currentVisits = cvd.getVisitsInRange();
                    }
                    if (cvd.getEmail().equals(cvdNext.getEmail())) {   // compare if the object contain duplicated emails -> in that case they are equal
                        duplicateCounter++;
                        indexesForDeletion.add(i + 1);
                        Date moveDate = cvdNext.getVisitsInRange().get(0);
                        currentVisits.add(moveDate);
                    } else {
                        duplicateCounter = 0;
                    }
                    result.get(i).setVisitsInRange(currentVisits);  //move date to previous object
                }
            }

//            for (int k = 0; k < indexesForDeletion.size(); k++) {
//                result.remove((int)indexesForDeletion.get(k)); //remove duplicated views
//            }


            for (int k = 0, l = 0; k < result.size(); k++) {
                if (k == indexesForDeletion.get(l)) {
                    l++;
                    if (l == indexesForDeletion.size()) {
                        l = 0;
                    }
                } else {
                    newResult.add(result.get(k));
                }
            }
            return newResult;
        }
    }

    /*
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PersonalInfo> query = criteriaBuilder.createQuery(PersonalInfo.class);
            List<Predicate> predicates = List.copyOf(getFilterModelAndDateResults(criteriaBuilder, model, dateFrom, dateTo));
            Predicate predicate = getPredicate(criteriaBuilder, query, firstName, lastName, email, phoneNumber);
            predicates.add(predicate);
            return session.createQuery(query.where(predicates.get(0), predicates.get(1), predicates.get(2),
                    predicates.get(3), predicates.get(4), predicates.get(5), predicates.get(6))).getResultList();

            //return session.createQuery(query.where(predicate)).getResultList();

            //return getPersonalInfoList(firstName, lastName, email, phoneNumber, session);
    */

    /*
    private List<Predicate> getFilterModelAndDateResults(CriteriaBuilder cb,
                                                         Optional<String> model,
                                                         Optional<Date> dateFrom,
                                                         Optional<Date> dateTo) {

        try (Session session = sessionFactory.openSession()) {

            CriteriaQuery query = cb.createQuery();

            //Root<PersonalInfo> personalInfoRoot = query.from(PersonalInfo.class);
            Root<Automobile> automobileRoot = query.from(Automobile.class);
            Root<Model> modelInfoRoot = query.from(Model.class);
            Root<User> userRoot = query.from(User.class);
            Root<CarService> csRoot = query.from(CarService.class);

            List<Predicate> result = new ArrayList<Predicate>();

            if (dateFrom.isPresent() && dateTo.isPresent()) {

                Join<Invoice, CarService> csInvoiceJoin = csRoot.join("invoice", JoinType.INNER);
                Join<Automobile, CarService> csAutomobileJoin = csRoot.join("automobile", JoinType.INNER);
                Join<User, Automobile> automobileUserJoin = automobileRoot.join("user", JoinType.INNER);
                Join<PersonalInfo, User> userPersonalInfoJoin = userRoot.join("personalInfo", JoinType.INNER);

                Predicate csInvoicePredicate = cb.and(cb.equal(csRoot.get("invoice"), "invoice_id"),
                        cb.lessThanOrEqualTo(csInvoiceJoin.get("date"), dateTo.get()),
                        cb.greaterThanOrEqualTo(csInvoiceJoin.get("date"), dateFrom.get()));
                Predicate csAutomobilePredicate = cb.and(cb.equal(csRoot.get("automobile"), "car_id"));
                Predicate automobileUserPredicate = cb.and(cb.equal(automobileRoot.get("user"), "owner_id"));
                Predicate userPersonalInfoPredicate = cb.and(cb.equal(userRoot.get("personalInfo"), "info_id"));
                result.add(csInvoicePredicate);
                result.add(csAutomobilePredicate);
                result.add(automobileUserPredicate);
                result.add(userPersonalInfoPredicate);
            }

            if (model.isPresent()) {
                Predicate automobileModelPredicate = cb.and(cb.equal(automobileRoot.get("model"), model),
                        cb.equal(automobileRoot.get("user"), "placeholder"));
                Predicate userInfoPredicate = cb.and(cb.equal(userRoot.get("personalInfo"), "placeholder"));
                result.add(automobileModelPredicate);
                result.add(userInfoPredicate);
            }

            return result;
        }
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                                   CriteriaQuery<PersonalInfo> query,
                                   Optional<String> firstName,
                                   Optional<String> lastName,
                                   Optional<String> email,
                                   Optional<String> phoneNumber) {

        Root<PersonalInfo> root = query.from(PersonalInfo.class);
        List<Predicate> list = new ArrayList<>();

        if (firstName.isPresent() && !firstName.get().isEmpty()) {
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%" + firstName.get() + "%");
            list.add(firstNamePredicate);
        }

        if (lastName.isPresent() && !lastName.get().isEmpty()) {
            Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), "%" + lastName.get() + "%");
            list.add(lastNamePredicate);
        }

        if (email.isPresent()) {
            Predicate emailPredicate = criteriaBuilder.equal(root.get("email"), email.get());
            list.add(emailPredicate);
        }

        if (phoneNumber.isPresent()) {
            Predicate phoneNumberPredicate = criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber.get());
            list.add(phoneNumberPredicate);
        }

        return criteriaBuilder.and(list.toArray(Predicate[]::new));
    }
    */

}