Feature: Validating Place Api

  @AddPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload "<name>" "<language>" "<address>"
    When I send a "POST" http request to "AddPlaceAPI" endpoint
    Then I validate that API call is a success with status code 200
    And  "status" in the response body is "OK"
    And  "scope" in the response body is "APP"
    And I validate place_id maps to the same "<name>" using "GetPlaceAPI"
    Examples:
      | name               | language | address                   |
      | World Class Center | English  | 29, side layout, cohen 09 |
      | BBClass Center     | Spanish  | Sea Cross Center          |

@DeletePlace
  Scenario: Validate Delete Place API
  Given DeletePlace payload
  When I send a "Delete" Request to "DeletePlaceAPI" endpoint
  Then I validate that the status code is 200
  And I validate that the status in the response body is "OK"