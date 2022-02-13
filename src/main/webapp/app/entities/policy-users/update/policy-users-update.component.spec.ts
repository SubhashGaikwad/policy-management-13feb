import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PolicyUsersService } from '../service/policy-users.service';
import { IPolicyUsers, PolicyUsers } from '../policy-users.model';
import { IUsersType } from 'app/entities/users-type/users-type.model';
import { UsersTypeService } from 'app/entities/users-type/service/users-type.service';

import { PolicyUsersUpdateComponent } from './policy-users-update.component';

describe('PolicyUsers Management Update Component', () => {
  let comp: PolicyUsersUpdateComponent;
  let fixture: ComponentFixture<PolicyUsersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let policyUsersService: PolicyUsersService;
  let usersTypeService: UsersTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PolicyUsersUpdateComponent],
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
      .overrideTemplate(PolicyUsersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PolicyUsersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    policyUsersService = TestBed.inject(PolicyUsersService);
    usersTypeService = TestBed.inject(UsersTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call usersType query and add missing value', () => {
      const policyUsers: IPolicyUsers = { id: 456 };
      const usersType: IUsersType = { id: 52753 };
      policyUsers.usersType = usersType;

      const usersTypeCollection: IUsersType[] = [{ id: 59550 }];
      jest.spyOn(usersTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: usersTypeCollection })));
      const expectedCollection: IUsersType[] = [usersType, ...usersTypeCollection];
      jest.spyOn(usersTypeService, 'addUsersTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      expect(usersTypeService.query).toHaveBeenCalled();
      expect(usersTypeService.addUsersTypeToCollectionIfMissing).toHaveBeenCalledWith(usersTypeCollection, usersType);
      expect(comp.usersTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const policyUsers: IPolicyUsers = { id: 456 };
      const usersType: IUsersType = { id: 78376 };
      policyUsers.usersType = usersType;

      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(policyUsers));
      expect(comp.usersTypesCollection).toContain(usersType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsers>>();
      const policyUsers = { id: 123 };
      jest.spyOn(policyUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policyUsers }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(policyUsersService.update).toHaveBeenCalledWith(policyUsers);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsers>>();
      const policyUsers = new PolicyUsers();
      jest.spyOn(policyUsersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: policyUsers }));
      saveSubject.complete();

      // THEN
      expect(policyUsersService.create).toHaveBeenCalledWith(policyUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PolicyUsers>>();
      const policyUsers = { id: 123 };
      jest.spyOn(policyUsersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ policyUsers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(policyUsersService.update).toHaveBeenCalledWith(policyUsers);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackUsersTypeById', () => {
      it('Should return tracked UsersType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUsersTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
