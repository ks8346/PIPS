import { Teams } from './teams';

describe('Teams', () => {
  it('should create an instance', () => {
    expect(new Teams(1,"Team1")).toBeTruthy();
  });
});
