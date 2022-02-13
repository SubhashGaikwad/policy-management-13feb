import { IPolicy } from 'app/entities/policy/policy.model';

export interface IMember {
  id?: number;
  name?: number | null;
  age?: number | null;
  relation?: string | null;
  contactNo?: number | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policy?: IPolicy | null;
}

export class Member implements IMember {
  constructor(
    public id?: number,
    public name?: number | null,
    public age?: number | null,
    public relation?: string | null,
    public contactNo?: number | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policy?: IPolicy | null
  ) {}
}

export function getMemberIdentifier(member: IMember): number | undefined {
  return member.id;
}
