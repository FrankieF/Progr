-- Frankie Fasola
-- Programming Languages Ada homework
-- Professor Tinkham

with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;

procedure ElectionPrediction is

        MaxCandidates: constant Integer := 100;

	-- We need to make the string types for the data of the candidates
        subtype StateAbbreviation is String (1..2);
        subtype Initials is String (1..2);

	-- The record stores the candidate information for printing to the screen
        type CandidateInfo is record
                CandidateInitials:  Initials;
                CandidateScore: Integer;
        end record;

	-- Defines two array types
	-- The first is of the type defined above and stores the candidates
	-- The second is more of a generic array because do not know the size of the array
	-- but we do need the arrays to be the same type for comparisons
        type ScoreArray is array (1..MaxCandidates) of CandidateInfo;
        type StatesArray is array (Positive range <>) of StateAbbreviation;

	
        Score: ScoreArray;
        CurrentState, HomeState: StateAbbreviation;
        CandidateName: Initials;
        NumberOfCandidates: Integer;
        Commercials: Integer;
        Days: Integer;
        Wins: Integer;
        Space: Character;

        -- Takes in the number of commercials aired and returns 1 point for every 10
        function CalculatePointsFromCommericals(Commercials: Integer) return Integer is
        begin
        return Commercials / 10;
        end CalculatePointsFromCommericals;

        -- Returns 5 points for every primary run
        function CalculatePointsFromWins(Wins: Integer) return Integer is
        begin
        return Wins * 5;
        end CalculatePointsFromWins;

        -- Takes the current state and the candidates state
        -- if the states are the same 50 points are added to the return value
        -- if the candidates state and the current state are in the same region
        -- 20 points are added to the return value
        function CalculatePointsFromState(CurrentState: StateAbbreviation; CandidateState: StateAbbreviation) return Integer is
                Total: Integer := 0;
                temp: Integer := 0;

        NewEngland : constant StatesArray := ("ME", "NH", "VT", "MA", "CT", "RI");
        NorthEast : constant StatesArray := ("NY", "PA", "NJ", "DE", "MD");
        SouthEast : constant StatesArray := ("VA", "NC", "SC", "GA", "FL", "AL", "MS", "TN", "KY", "WV", "AR", "LA");
        Lakes : constant StatesArray := ("OH", "MI", "IN", "IL", "WI", "MN");
        Central : constant StatesArray:= ("IA", "MO", "ND", "SD", "NE", "KS", "OK", "TX");
        West : constant StatesArray := ("MT", "WY", "CO", "NM", "AZ", "UT", "ID", "NV");
        Pacific : constant StatesArray :=("WA", "OR", "CA", "AK", "HI");

begin

                if CandidateState = CurrentState then Total := Total + 50; end if;

		-- Check if the states are in the same region and increment a variable
		-- If the temp var is 2 then we know the states are in the same region
		-- We return the total after adding to it
		-- If the temp var is not 2 then we know they are not in the same region
		-- Reset temp and check the next region
                for I in NewEngland'range loop
                        if CurrentState = NewEngland(I) then temp := temp + 1; end if;
                        if CandidateState = NewEngland(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20;
                end if;
                temp := 0;

                for I in NorthEast'range loop
                        if CurrentState = NorthEast(I) then temp := temp + 1; end if;
                        if CandidateState = NorthEast(I) then temp := temp + 1; end if;
                end loop;

                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in SouthEast'range loop
                        if CurrentState = SouthEast(I) then temp := temp + 1; end if;
                        if CandidateState = SouthEast(I) then temp := temp + 1; end if;
                end loop;

                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in Lakes'range loop
                        if CurrentState = Lakes(I) then temp := temp + 1; end if;
                        if CandidateState = Lakes(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in Central'range loop
                        if CurrentState = Central(I) then temp := temp + 1; end if;
                        if CandidateState = Central(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return Total + 20; end if;
                temp := 0;

                for I in West'range loop
                        if CurrentState = West(I) then temp := temp + 1;end if;
                        if CandidateState = West(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in Pacific'range loop
                        if CurrentState = Pacific(I) then temp := temp + 1; end if;
                        if CandidateState = Pacific(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20; end if;

                return 0;
        end CalculatePointsFromState;

begin

	-- Take in the input for our program
        Get(CurrentState);
        Get(NumberOfCandidates);
        Skip_Line;

	-- Get the candidate information and then add the total and print

        for I in 1..NumberOfCandidates loop
                Get(CandidateName);
                Get(Space);
                Get(HomeState);
                Get(Commercials);
                Get(Days);
                Get(Wins);
                Skip_Line;

                Score(I).CandidateInitials := CandidateName;
                Score(I).CandidateScore  := Days + CalculatePointsFromCommericals(Commercials) +
                CalculatePointsFromWins(Wins) + CalculatePointsFromState(CurrentState, HomeState);
        end loop;

        for I in 1..NumberOfCandidates loop
                Put(Score(I).CandidateInitials);
                Put(Score(I).CandidateScore);
                New_Line;
        end loop;

end ElectionPrediction;


-- Frankie Fasola
-- -- Programming Languages Ada homework
--
with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;

procedure ElectionPrediction is
        MaxCandidates: constant Integer := 100;
        subtype StateAbbreviation is String (1..2);
        subtype Initials is String (1..2);
        type CandidateInfo is record
                CandidateInitials:  Initials;
                CandidateScore: Integer;
        end record;

        type ScoreArray is array (1..MaxCandidates) of CandidateInfo;
        type StatesArray is array (Positive range <>) of StateAbbreviation;

        Score: ScoreArray;
        CurrentState, HomeState: StateAbbreviation;
        CandidateName: Initials;
        NumberOfCandidates: Integer;
        Commercials: Integer;
        Days: Integer;
        Wins: Integer;
        Space: Character;

        -- Takes in the number of commercials aired and returns 1 point for every 10
        function CalculatePointsFromCommericals(Commercials: Integer) return Integer is
        begin
        return Commercials / 10;
        end CalculatePointsFromCommericals;

        -- Returns 5 points for every primary run
        function CalculatePointsFromWins(Wins: Integer) return Integer is
        begin
        return Wins * 5;
        end CalculatePointsFromWins;

        -- Takes the current state and the candidates state
        -- if the states are the same 50 points are added to the return value
        -- if the candidates state and the current state are in the same region
        -- 20 points are added to the return value
        function CalculatePointsFromState(CurrentState: StateAbbreviation; CandidateState: StateAbbreviation) return Integer is
                Total: Integer := 0;
                temp: Integer := 0;

        NewEngland : constant StatesArray := ("ME", "NH", "VT", "MA", "CT", "RI");
        NorthEast : constant StatesArray := ("NY", "PA", "NJ", "DE", "MD");
        SouthEast : constant StatesArray := ("VA", "NC", "SC", "GA", "FL", "AL", "MS", "TN", "KY", "WV", "AR", "LA");
        Lakes : constant StatesArray := ("OH", "MI", "IN", "IL", "WI", "MN");
        Central : constant StatesArray:= ("IA", "MO", "ND", "SD", "NE", "KS", "OK", "TX");
        West : constant StatesArray := ("MT", "WY", "CO", "NM", "AZ", "UT", "ID", "NV");
        Pacific : constant StatesArray :=("WA", "OR", "CA", "AK", "HI");

begin

                if CandidateState = CurrentState then Total := Total + 50;
                end if;
                for I in NewEngland'range loop
                        if CurrentState = NewEngland(I) then temp := temp + 1; end if;
                        if CandidateState = NewEngland(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20;
                end if;
                temp := 0;

                for I in NorthEast'range loop
                        if CurrentState = NorthEast(I) then temp := temp + 1; end if;
                        if CandidateState = NorthEast(I) then temp := temp + 1; end if;
                end loop;

                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in SouthEast'range loop
                        if CurrentState = SouthEast(I) then temp := temp + 1; end if;
                        if CandidateState = SouthEast(I) then temp := temp + 1; end if;
                end loop;

                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in Lakes'range loop
                        if CurrentState = Lakes(I) then temp := temp + 1; end if;
                        if CandidateState = Lakes(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in Central'range loop
                        if CurrentState = Central(I) then temp := temp + 1; end if;
                        if CandidateState = Central(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return Total + 20; end if;
                temp := 0;

                for I in West'range loop
                        if CurrentState = West(I) then temp := temp + 1;end if;
                        if CandidateState = West(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20; end if;
                temp := 0;

                for I in Pacific'range loop
                        if CurrentState = Pacific(I) then temp := temp + 1; end if;
                        if CandidateState = Pacific(I) then temp := temp + 1; end if;
                end loop;
                if temp = 2 then return  Total + 20; end if;

                return 0;
        end CalculatePointsFromState;
begin

        Get(CurrentState);
        Get(NumberOfCandidates);
        Skip_Line;
        for I in 1..NumberOfCandidates loop
                Get(CandidateName);
                Get(Space);
                Get(HomeState);
                Get(Commercials);
                Get(Days);
                Get(Wins);
                Skip_Line;

                Score(I).CandidateInitials := CandidateName;
                Score(I).CandidateScore  := Days + CalculatePointsFromCommericals(Commercials) +
                CalculatePointsFromWins(Wins) + CalculatePointsFromState(CurrentState, HomeState);
        end loop;

        for I in 1..NumberOfCandidates loop
                Put(Score(I).CandidateInitials);
                Put(Score(I).CandidateScore);
                New_Line;
        end loop;

end ElectionPrediction;
