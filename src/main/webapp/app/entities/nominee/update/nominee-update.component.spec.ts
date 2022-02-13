import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NomineeService } from '../service/nominee.service';
import { INominee, Nominee } from '../nominee.model';
import { IPolicy } from 'app/entities/policy/policy.model';
import { PolicyService } from 'app/entities/policy/service/policy.service';

import { NomineeUpdateComponent } from './nominee-update.component';

describe('Nominee Management Update Component', () => {
  let comp: NomineeUpdateComponent;
  let fixture: ComponentFixture<NomineeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nomineeService: NomineeService;
  let policyService: PolicyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NomineeUpdateComponent],
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
      .overrideTemplate(NomineeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NomineeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nomineeService = TestBed.inject(NomineeService);
    policyService = TestBed.inject(PolicyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Policy query and add missing value', () => {
      const nominee: INominee = { id: 456 };
      const policy: IPolicy = { id: 26565 };
      nominee.policy = policy;

      const policyCollection: IPolicy[] = [{ id: 37512 }];
      jest.spyOn(policyService, 'query').mockReturnValue(of(new HttpResponse({ body: policyCollection })));
      const additionalPolicies = [policy];
      const expectedCollection: IPolicy[] = [...additionalPolicies, ...policyCollection];
      jest.spyOn(policyService, 'addPolicyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      expect(policyService.query).toHaveBeenCalled();
      expect(policyService.addPolicyToCollectionIfMissing).toHaveBeenCalledWith(policyCollection, ...additionalPolicies);
      expect(comp.policiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nominee: INominee = { id: 456 };
      const policy: IPolicy = { id: 53938 };
      nominee.policy = policy;

      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nominee));
      expect(comp.policiesSharedCollection).toContain(policy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nominee>>();
      const nominee = { id: 123 };
      jest.spyOn(nomineeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nominee }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(nomineeService.update).toHaveBeenCalledWith(nominee);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nominee>>();
      const nominee = new Nominee();
      jest.spyOn(nomineeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nominee }));
      saveSubject.complete();

      // THEN
      expect(nomineeService.create).toHaveBeenCalledWith(nominee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nominee>>();
      const nominee = { id: 123 };
      jest.spyOn(nomineeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nomineeService.update).toHaveBeenCalledWith(nominee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPolicyById', () => {
      it('Should return tracked Policy primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPolicyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
