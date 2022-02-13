import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UsersTypeService } from '../service/users-type.service';
import { IUsersType, UsersType } from '../users-type.model';

import { UsersTypeUpdateComponent } from './users-type-update.component';

describe('UsersType Management Update Component', () => {
  let comp: UsersTypeUpdateComponent;
  let fixture: ComponentFixture<UsersTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usersTypeService: UsersTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UsersTypeUpdateComponent],
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
      .overrideTemplate(UsersTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsersTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usersTypeService = TestBed.inject(UsersTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const usersType: IUsersType = { id: 456 };

      activatedRoute.data = of({ usersType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(usersType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UsersType>>();
      const usersType = { id: 123 };
      jest.spyOn(usersTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usersType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usersType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(usersTypeService.update).toHaveBeenCalledWith(usersType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UsersType>>();
      const usersType = new UsersType();
      jest.spyOn(usersTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usersType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usersType }));
      saveSubject.complete();

      // THEN
      expect(usersTypeService.create).toHaveBeenCalledWith(usersType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<UsersType>>();
      const usersType = { id: 123 };
      jest.spyOn(usersTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usersType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usersTypeService.update).toHaveBeenCalledWith(usersType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
