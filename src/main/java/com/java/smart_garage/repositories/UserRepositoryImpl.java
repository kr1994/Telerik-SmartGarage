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

//            List<PersonalInfo> result = new ArrayList<PersonalInfo>();
//            List<CustomerViewDto> resultDto = new ArrayList<CustomerViewDto>();
//            Set<PersonalInfo> resultSet = new HashSet<PersonalInfo>();
//            PersonalInfoRepository pir = new PersonalInfoRepositoryImpl(sessionFactory);
//            List<PersonalInfo> allPersonalInformation = pir.getAllPersonalInformations();
//            Set<Date> visits = new HashSet<>();
//            if (firstName.isPresent() || lastName.isPresent() || email.isPresent() || phoneNumber.isPresent()) {
//                Set<PersonalInfo> namesSet = new HashSet<PersonalInfo>();
//                for (PersonalInfo pi: allPersonalInformation) {
//
//                    if (firstName.isPresent()) {
//                        if (pi.getFirstName().equals(firstName.get())) {
//                            namesSet.add(pi);
//                        }
//                    }
//
//                    if (lastName.isPresent()) {
//                        if (pi.getLastName().equals(lastName.get())) {
//                            namesSet.add(pi);
//                        }
//                    }
//
//                    if (email.isPresent()) {
//                        if (pi.getEmail().equals(email.get())) {
//                            namesSet.add(pi);
//                        }
//                    }
//
//                    if (phoneNumber.isPresent()) {
//                        if (pi.getPhoneNumber().equals(phoneNumber.get())) {
//                            namesSet.add(pi);
//                        }
//                    }
//                }
//
//                resultSet.addAll(namesSet);
//
//            }
//
//            if (model.isPresent()) {
//                ModelRepository mr = new ModelRepositoryImpl(sessionFactory);
//                AutomobileRepository ar = new AutomobileRepositoryImpl(sessionFactory);
//                Model modelObject = mr.getByName(model.get());
//
//                List<Automobile> automobiles = ar.getAllCars().stream()
//                                               .filter(a -> a.getModel().getModelName().equals(modelObject.getModelName()))
//                                               .collect(Collectors.toList());
//
//                Set<User> owners = new HashSet<User>();
//                for (Automobile a: automobiles) {
//                    owners.add(a.getOwner());
//                }
//
//                Set<PersonalInfo> modelsSet = new HashSet<PersonalInfo>();
//                for (User u: owners) {
//                    if (!u.isEmployee()) {
//                        modelsSet.add(u.getPersonalInfo());
//                    }
//                }
//
//                resultSet.addAll(modelsSet);
//
//            }
//
//            if (dateFrom.isPresent() && dateTo.isPresent()) {
//                InvoiceRepository ir = new InvoiceRepositoryImpl(sessionFactory);
//                List<Invoice> invoices = ir.getAllInvoices().stream()
//                                         .filter(i -> i.getDate().before(dateTo.get()) && i.getDate().after(dateFrom.get()))
//                                         .collect(Collectors.toList());
//
//
//                for (Invoice invoice : invoices) {
//                    visits.add(invoice.getDate());
//                }
//                CarServiceRepository csr = new CarServiceRepositoryImpl(sessionFactory);
//                List<CarService> allCarServices = csr.getAllCarServices();
//                Set<CarService> carServices = new HashSet<>();
//
//                for (Invoice i: invoices) {
//                    for (CarService cs: allCarServices) {
//                        if (cs.getInvoice().getInvoiceId() == i.getInvoiceId()) {
//                            carServices.add(cs);
//                        }
//                    }
//                }
//
//                Set<Automobile> cars = new HashSet<>();
//                for (CarService cs: carServices) {
//                    cars.add(cs.getCar());
//                }
//
//                Set<User> owners = new HashSet<User>();
//                for (Automobile a: cars) {
//                    owners.add(a.getOwner());
//                }
//
//                Set<PersonalInfo> datesSet = new HashSet<PersonalInfo>();
//                for (User u: owners) {
//                    if (!u.isEmployee()) {
//                        datesSet.add(u.getPersonalInfo());
//                    }
//                }
//                resultSet.addAll(datesSet);
//            }
//            result.addAll(resultSet);
//
//            for (PersonalInfo pi: resultSet) {
//                CustomerViewDto cvd = new CustomerViewDto();
//                modelConversionHelper.personalInfoToCustomerViewDtoObject(pi,
//                        model.orElse(""), new ArrayList<>(visits));
//                resultDto.add(cvd);
//            }
//
//            return resultDto;

            List<CustomerViewDto> result = new ArrayList<CustomerViewDto>();
            if (firstName.isPresent() || lastName.isPresent() || email.isPresent() || phoneNumber.isPresent()) {
                String queryStr = "from User u where ";

                if (firstName.isPresent()) {
                    queryStr += " personalInfo.firstName = :firstName";
                }

                if (lastName.isPresent()) {
                    queryStr += " personalInfo.lastName = :lastName";
                }

                if (email.isPresent()) {
                    queryStr += " personalInfo.email = :email";
                }

                if (phoneNumber.isPresent()) {
                    queryStr += " personalInfo.phoneNumber = :phoneNumber";
                }

                if (firstName.isPresent() || lastName.isPresent() || email.isPresent() || phoneNumber.isPresent()) {
                    queryStr += " and userType.type = :customer";
                }

                Query<User> queryForPi = session.createQuery(queryStr, User.class);

                if (firstName.isPresent()) {
                    queryForPi.setParameter("firstName", firstName.get());
                }

                if (lastName.isPresent()) {
                    queryForPi.setParameter("lastName", lastName.get());
                }

                if (email.isPresent()) {
                    queryForPi.setParameter("email", email.get());
                }

                if (phoneNumber.isPresent()) {
                    queryForPi.setParameter("phoneNumber", phoneNumber.get());
                }

                queryForPi.setParameter("customer", "Customer");

                if (!queryForPi.getResultList().isEmpty()) {
                    for (User u : queryForPi.getResultList()) {
                        CustomerViewDto cvd = new CustomerViewDto();
                        cvd.setFirstName(u.getPersonalInfo().getFirstName());
                        cvd.setLastName(u.getPersonalInfo().getLastName());
                        cvd.setEmail(u.getPersonalInfo().getEmail());
                        cvd.setPhoneNumber(u.getPersonalInfo().getPhoneNumber());
                        result.add(cvd);
                    }
                }
            }
            Query<User> queryForModel;

            if (model.isPresent()) {
                queryForModel = session.createQuery("select u from User u join Automobile a " +
                        "where a.model.modelName = :model and u.userType.type = :customer", User.class);

                queryForModel.setParameter("model", model.get());
                queryForModel.setParameter("customer", "Customer");

                for (User u: queryForModel.getResultList()) {
                    if (!result.isEmpty()) {
                        for (CustomerViewDto cvd: result) {
                            if (cvd.getEmail().equals(u.getPersonalInfo().getEmail())) {
                                cvd.setCarModel(model.get());
                            } else {
                                result.remove(cvd);
                            }
                        }
                    } else {
                        CustomerViewDto cvd = new CustomerViewDto();
                        cvd.setFirstName(u.getPersonalInfo().getFirstName());
                        cvd.setLastName(u.getPersonalInfo().getLastName());
                        cvd.setEmail(u.getPersonalInfo().getEmail());
                        cvd.setPhoneNumber(u.getPersonalInfo().getPhoneNumber());
                        cvd.setCarModel(model.get());
                        result.add(cvd);
                    }

                }
            }

            if (dateFrom.isPresent() && dateTo.isPresent()) {
                Date dateStart = dateFrom.get();
                Date dateEnd = dateTo.get();
                List<Date> visits = new ArrayList<Date>();
                Query<User> queryForVisitors = session.createQuery("select u from User u join Automobile a " +
                        "join CarService cs where cs.invoice.date >= :dateStart and cs.invoice.date <= :dateEnd " +
                        "and cs.automobile.user.userType.type = :customer", User.class);

                queryForVisitors.setParameter("dateStart", dateStart);
                queryForVisitors.setParameter("dateEnd", dateEnd);
                queryForVisitors.setParameter("customer", "Customer");

                List<CarService> carServices = new ArrayList<CarService>();

//                Query<java.util.Date> queryForVisits = session.createQuery("select i.date from Invoice i " +
//                        "join CarService cs where i.date >= :dateStart and i.date <= :dateEnd and " +
//                        "cs.automobile.user.userType.type = :customer ", java.util.Date.class);

                Query<CarService> queryForVisits = session.createQuery("from CarService cs " +
                         "where invoice.date >= :dateStart and invoice.date <= :dateEnd and " +
                         "automobile.user.userType.type = :customer ", CarService.class);

                queryForVisits.setParameter("dateStart", dateStart);
                queryForVisits.setParameter("dateEnd", dateEnd);
                queryForVisits.setParameter("customer", "Customer");

                carServices.addAll(queryForVisits.getResultList());

                for (CarService cs: queryForVisits.getResultList()) {
                    if (!result.isEmpty()) {
                        for (CustomerViewDto cvd: result) {
                            if (cvd.getEmail().equals(cs.getCar().getOwner().getPersonalInfo().getEmail())) {
                                cvd.setVisitsInRange(List.of(cs.getInvoice().getDate()));
                            } else {
                                result.remove(cvd);
                            }
                        }
                    } else {
                        CustomerViewDto cvd = new CustomerViewDto();
                        cvd.setVisitsInRange(List.of(cs.getInvoice().getDate()));
                        result.add(cvd);
                    }

                }
            }


            //return query.stream().filter(u -> !(u.isEmployee())).collect(Collectors.toList());
            return result;

        }

    }


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


    private Predicate getCarPredicate(CriteriaBuilder criteriaBuilder,
                                      CriteriaQuery<PersonalInfo> query,
                                      AutomobileRepository automobileRepository,
                                      Optional<String> modelAutomobile,
                                      List<Optional<Date>> visits) {

        try (Session session = sessionFactory.openSession()) {




            if (visits.get(0).isPresent()) {
                Date dateFrom = visits.get(0).get();
                Date dateTo = visits.get(1).get();
                Query<PersonalInfo> queryForVisits = session.createQuery("from Automobile a join CarService cs " +
                        "join Invoice i join User u join PersonalInfo p where i.date >= :dateFrom and " +
                        " i.date <= :dateTo and u.userType != :employee", PersonalInfo.class);

                queryForVisits.setParameter("dateFrom", dateFrom);
                queryForVisits.setParameter("dateTo", dateTo);
                queryForVisits.setParameter("employee", "Employee");
            }

        }
        Root<PersonalInfo> root = query.from(PersonalInfo.class);
        List<Predicate> list = new ArrayList<>();

        return criteriaBuilder.and(list.toArray(Predicate[]::new));
    }


    */
}