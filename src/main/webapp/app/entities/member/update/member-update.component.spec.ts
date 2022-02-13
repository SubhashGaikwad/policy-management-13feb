import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberService } from '../service/member.service';
import { IMember, Member } from '../member.model';
import { IPolicy } from 'app/entities/policy/policy.model';
import { PolicyService } from 'app/entities/policy/service/policy.service';

import { MemberUpdateComponent } from './member-update.component';

describe('Member Management Update Component', () => {
  let comp: MemberUpdateComponent;
  let fixture: ComponentFixture<MemberUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberService: MemberService;
  let policyService: PolicyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberUpdateComponent],
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
      .overrideTemplate(MemberUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberService = TestBed.inject(MemberService);
    policyService = TestBed.inject(PolicyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Policy query and add missing value', () => {
      const member: IMember = { id: 456 };
      const policy: IPolicy = { id: 22858 };
      member.policy = policy;

      const policyCollection: IPolicy[] = [{ id: 20610 }];
      jest.spyOn(policyService, 'query').mockReturnValue(of(new HttpResponse({ body: policyCollection })));
      const additionalPolicies = [policy];
      const expectedCollection: IPolicy[] = [...additionalPolicies, ...policyCollection];
      jest.spyOn(policyService, 'addPolicyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ member });
      comp.ngOnInit();

      expect(policyService.query).toHaveBeenCalled();
      expect(policyService.addPolicyToCollectionIfMissing).toHaveBeenCalledWith(policyCollection, ...additionalPolicies);
      expect(comp.policiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const member: IMember = { id: 456 };
      const policy: IPolicy = { id: 43167 };
      member.policy = policy;

      activatedRoute.data = of({ member });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(member));
      expect(comp.policiesSharedCollection).toContain(policy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Member>>();
      const member = { id: 123 };
      jest.spyOn(memberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ member });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: member }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberService.update).toHaveBeenCalledWith(member);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Member>>();
      const member = new Member();
      jest.spyOn(memberService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ member });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: member }));
      saveSubject.complete();

      // THEN
      expect(memberService.create).toHaveBeenCalledWith(member);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Member>>();
      const member = { id: 123 };
      jest.spyOn(memberService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ member });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberService.update).toHaveBeenCalledWith(member);
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
