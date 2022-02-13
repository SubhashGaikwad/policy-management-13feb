import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsersTypeDetailComponent } from './users-type-detail.component';

describe('UsersType Management Detail Component', () => {
  let comp: UsersTypeDetailComponent;
  let fixture: ComponentFixture<UsersTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UsersTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ usersType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UsersTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UsersTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load usersType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.usersType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
