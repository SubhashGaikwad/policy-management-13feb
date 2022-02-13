import { IPolicyUsers } from 'app/entities/policy-users/policy-users.model';

export interface IUsersType {
  id?: number;
  name?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policyUsers?: IPolicyUsers | null;
}

export class UsersType implements IUsersType {
  constructor(
    public id?: number,
    public name?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policyUsers?: IPolicyUsers | null
  ) {}
}

export function getUsersTypeIdentifier(usersType: IUsersType): number | undefined {
  return usersType.id;
}
