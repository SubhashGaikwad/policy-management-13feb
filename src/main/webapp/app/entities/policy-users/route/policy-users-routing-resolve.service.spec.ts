import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPolicyUsers, PolicyUsers } from '../policy-users.model';
import { PolicyUsersService } from '../service/policy-users.service';

import { PolicyUsersRoutingResolveService } from './policy-users-routing-resolve.service';

describe('PolicyUsers routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PolicyUsersRoutingResolveService;
  let service: PolicyUsersService;
  let resultPolicyUsers: IPolicyUsers | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(PolicyUsersRoutingResolveService);
    service = TestBed.inject(PolicyUsersService);
    resultPolicyUsers = undefined;
  });

  describe('resolve', () => {
    it('should return IPolicyUsers returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPolicyUsers = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPolicyUsers).toEqual({ id: 123 });
    });

    it('should return new IPolicyUsers if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPolicyUsers = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPolicyUsers).toEqual(new PolicyUsers());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PolicyUsers })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPolicyUsers = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPolicyUsers).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
