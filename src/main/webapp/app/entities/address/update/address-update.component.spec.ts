import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AddressService } from '../service/address.service';
import { IAddress, Address } from '../address.model';
import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';
import { PolicyUsersService } from 'app/entities/policy-users/service/policy-users.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { AddressUpdateComponent } from './address-update.component';

describe('Address Management Update Component', () => {
  let comp: AddressUpdateComponent;
  let fixture: ComponentFixture<AddressUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addressService: AddressService;
  let policyUsersService: PolicyUsersService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AddressUpdateComponent],
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
      .overrideTemplate(AddressUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addressService = TestBed.inject(AddressService);
    policyUsersService = TestBed.inject(PolicyUsersService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PolicyUsers query and add missing value', () => {
      const address: IAddress = { id: 456 };
      const policyUsers: IPolicyUsers = { id: 47946 };
      address.policyUsers = policyUsers;

      const policyUsersCollection: IPolicyUsers[] = [{ id: 5608 }];
      jest.spyOn(policyUsersService, 'query').mockReturnValue(of(new HttpResponse({ body: policyUsersCollection })));
      const additionalPolicyUsers = [policyUsers];
      const expectedCollection: IPolicyUsers[] = [...additionalPolicyUsers, ...policyUsersCollection];
      jest.spyOn(policyUsersService, 'addPolicyUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(policyUsersService.query).toHaveBeenCalled();
      expect(policyUsersService.addPolicyUsersToCollectionIfMissing).toHaveBeenCalledWith(policyUsersCollection, ...additionalPolicyUsers);
      expect(comp.policyUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const address: IAddress = { id: 456 };
      const company: ICompany = { id: 21226 };
      address.company = company;

      const companyCollection: ICompany[] = [{ id: 91421 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(companyCollection, ...additionalCompanies);
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const address: IAddress = { id: 456 };
      const policyUsers: IPolicyUsers = { id: 56472 };
      address.policyUsers = policyUsers;
      const company: ICompany = { id: 34882 };
      address.company = company;

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(address));
      expect(comp.policyUsersSharedCollection).toContain(policyUsers);
      expect(comp.companiesSharedCollection).toContain(company);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Address>>();
      const address = { id: 123 };
      jest.spyOn(addressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: address }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(addressService.update).toHaveBeenCalledWith(address);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Address>>();
      const address = new Address();
      jest.spyOn(addressService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: address }));
      saveSubject.complete();

      // THEN
      expect(addressService.create).toHaveBeenCalledWith(address);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Address>>();
      const address = { id: 123 };
      jest.spyOn(addressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addressService.update).toHaveBeenCalledWith(address);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPolicyUsersById', () => {
      it('Should return tracked PolicyUsers primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPolicyUsersById(0, entity);
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
  });
});
