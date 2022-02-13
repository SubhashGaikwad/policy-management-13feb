import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PolicyService } from '../service/policy.service';
import { IPolicy, Policy } from '../policy.model';
import { IAgency } from 'app/entities/agency/agency.model';
import { AgencyService } from 'app/entities/agency/service/agency.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IPremiunDetails } from 'app/entities/premiun-details/premiun-details.model';
import { PremiunDetailsService } from 'app/entities/premiun-details/service/premiun-details.service';
import { IVehicleClass } from 'app/entities/vehicle-class/vehicle-class.model';
import { VehicleClassService } from 'app/entities/vehicle-class/service/vehicle-class.service';
import { IBankDetails } from 'app/entities/bank-details/bank-details.model';
import { BankDetailsService } from 'app/entities/bank-details/service/bank-details.service';
import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';
import { PolicyUsersService } from 'app/entities/policy-users/service/policy-users.service';

import { PolicyUpdateComponent } from './policy-update.component';

describe('Policy Management Update Component', () => {
  let comp: PolicyUpdateComponent;
  let fixture: ComponentFixture<PolicyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let policyService: PolicyService;
  let agencyService: AgencyService;
  let companyService: CompanyService;
  let productService: ProductService;
  let premiunDetailsService: PremiunDetailsService;
  let vehicleClassService: VehicleClassService;
  let bankDetailsService: BankDetailsService;
  let policyUsersService: PolicyUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PolicyUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PolicyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PolicyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    policyService = TestBed.inject(PolicyService);
    agencyService = TestBed.inject(AgencyService);
    companyService = TestBed.inject(CompanyService);
    productService = TestBed.inject(ProductService);
    premiunDetailsService = TestBed.inject(PremiunDetailsService);
    vehicleClassService = TestBed.inject(VehicleClassService);
    bankDetailsService = TestBed.inject(BankDetailsService);
    policyUsersService = TestBed.inject(PolicyUsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call agency query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const agency: IAgency = { id: 28639 };
      policy.agency = agency;

      const agencyCollection: IAgency[] = [{ id: 31683 }];
      jest.spyOn(agencyService, 'query').mockReturnValue(of(new HttpResponse({ body: agencyCollection })));
      const expectedCollection: IAgency[] = [agency, ...agencyCollection];
      jest.spyOn(agencyService, 'addAgencyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(agencyService.query).toHaveBeenCalled();
      expect(agencyService.addAgencyToCollectionIfMissing).toHaveBeenCalledWith(agencyCollection, agency);
      expect(comp.agenciesCollection).toEqual(expectedCollection);
    });

    it('Should call company query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const company: ICompany = { id: 68426 };
      policy.company = company;

      const companyCollection: ICompany[] = [{ id: 47881 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const expectedCollection: ICompany[] = [company, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(companyCollection, company);
      expect(comp.companiesCollection).toEqual(expectedCollection);
    });

    it('Should call product query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const product: IProduct = { id: 40565 };
      policy.product = product;

      const productCollection: IProduct[] = [{ id: 19791 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const expectedCollection: IProduct[] = [product, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, product);
      expect(comp.productsCollection).toEqual(expectedCollection);
    });

    it('Should call premiunDetails query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const premiunDetails: IPremiunDetails = { id: 7582 };
      policy.premiunDetails = premiunDetails;

      const premiunDetailsCollection: IPremiunDetails[] = [{ id: 88824 }];
      jest.spyOn(premiunDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: premiunDetailsCollection })));
      const expectedCollection: IPremiunDetails[] = [premiunDetails, ...premiunDetailsCollection];
      jest.spyOn(premiunDetailsService, 'addPremiunDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(premiunDetailsService.query).toHaveBeenCalled();
      expect(premiunDetailsService.addPremiunDetailsToCollectionIfMissing).toHaveBeenCalledWith(premiunDetailsCollection, premiunDetails);
      expect(comp.premiunDetailsCollection).toEqual(expectedCollection);
    });

    it('Should call vehicleClass query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const vehicleClass: IVehicleClass = { id: 64633 };
      policy.vehicleClass = vehicleClass;

      const vehicleClassCollection: IVehicleClass[] = [{ id: 61436 }];
      jest.spyOn(vehicleClassService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleClassCollection })));
      const expectedCollection: IVehicleClass[] = [vehicleClass, ...vehicleClassCollection];
      jest.spyOn(vehicleClassService, 'addVehicleClassToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(vehicleClassService.query).toHaveBeenCalled();
      expect(vehicleClassService.addVehicleClassToCollectionIfMissing).toHaveBeenCalledWith(vehicleClassCollection, vehicleClass);
      expect(comp.vehicleClassesCollection).toEqual(expectedCollection);
    });

    it('Should call bankDetails query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const bankDetails: IBankDetails = { id: 78410 };
      policy.bankDetails = bankDetails;

      const bankDetailsCollection: IBankDetails[] = [{ id: 2335 }];
      jest.spyOn(bankDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: bankDetailsCollection })));
      const expectedCollection: IBankDetails[] = [bankDetails, ...bankDetailsCollection];
      jest.spyOn(bankDetailsService, 'addBankDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(bankDetailsService.query).toHaveBeenCalled();
      expect(bankDetailsService.addBankDetailsToCollectionIfMissing).toHaveBeenCalledWith(bankDetailsCollection, bankDetails);
      expect(comp.bankDetailsCollection).toEqual(expectedCollection);
    });

    it('Should call PolicyUsers query and add missing value', () => {
      const policy: IPolicy = { id: 456 };
      const policyUsers: IPolicyUsers = { id: 37639 };
      policy.policyUsers = policyUsers;

      const policyUsersCollection: IPolicyUsers[] = [{ id: 86772 }];
      jest.spyOn(policyUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: policyUsersCollection })));
      const additionalPolicyUsers = [policyUsers];
      const expectedCollection: IPolicyUsers[] = [...additionalPolicyUsers, ...policyUsersCollection];
      jest.spyOn(policyUsersService, 'addPolicyUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(policyUsersService.query).toHaveBeenCalled();
      expect(policyUsersService.addPolicyUsersToCollectionIfMissing).toHaveBeenCalledWith(policyUsersCollection, ...additionalPolicyUsers);
      expect(comp.policyUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const policy: IPolicy = { id: 456 };
      const agency: IAgency = { id: 19090 };
      policy.agency = agency;
      const company: ICompany = { id: 84590 };
      policy.company = company;
      const product: IProduct = { id: 79818 };
      policy.product = product;
      const premiunDetails: IPremiunDetails = { id: 65123 };
      policy.premiunDetails = premiunDetails;
      const vehicleClass: IVehicleClass = { id: 49256 };
      policy.vehicleClass = vehicleClass;
      const bankDetails: IBankDetails = { id: 20053 };
      policy.bankDetails = bankDetails;
      const policyUsers: IPolicyUsers = { id: 26783 };
      policy.policyUsers = policyUsers;

      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(policy));
      expect(comp.agenciesCollection).toContain(agency);
      expect(comp.companiesCollection).toContain(company);
      expect(comp.productsCollection).toContain(product);
      expect(comp.premiunDetailsCollection).toContain(premiunDetails);
      expect(comp.vehicleClassesCollection).toContain(vehicleClass);
      expect(comp.bankDetailsCollection).toContain(bankDetails);
      expect(comp.policyUsersSharedCollection).toContain(policyUsers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Policy>>();
      const policy = { id: 123 };
      jest.spyOn(policyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policy }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(policyService.update).toHaveBeenCalledWith(policy);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Policy>>();
      const policy = new Policy();
      jest.spyOn(policyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policy }));
      saveSubject.complete();

      // THEN
      expect(policyService.create).toHaveBeenCalledWith(policy);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Policy>>();
      const policy = { id: 123 };
      jest.spyOn(policyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(policyService.update).toHaveBeenCalledWith(policy);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAgencyById', () => {
      it('Should return tracked Agency primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAgencyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCompanyById', () => {
      it('Should return tracked Company primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCompanyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPremiunDetailsById', () => {
      it('Should return tracked PremiunDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPremiunDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackVehicleClassById', () => {
      it('Should return tracked VehicleClass primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVehicleClassById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackBankDetailsById', () => {
      it('Should return tracked BankDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBankDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPolicyUsersById', () => {
      it('Should return tracked PolicyUsers primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPolicyUsersById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
