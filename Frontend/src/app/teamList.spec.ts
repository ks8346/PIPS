import { teamList } from './teamList';

describe('TeamList', () => {
  it('should create an instance', () => {
    let teams = "Devs";
    expect(new teamList(teams)).toBeTruthy();
  });
});
