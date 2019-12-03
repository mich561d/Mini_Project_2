module Main exposing (..)

import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Http
import Json.Decode as Json


--Model


type alias Model =
    { page : Page
    , gamers : List Gamer
    }


type alias Page =
    { title : String }


type alias Gamer =
    { id : Int
    , nickname : String
    , score : Float
    }


init : ( Model, Cmd Msg )
init =
    ( Model (Page "Elm") [], Cmd.none )



--Update


type Msg
    = OpenGamers (Result Http.Error (List Gamer))
    | GetGamers
    | UpdateGamers String


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        OpenGamers (Ok json) ->
            ( { model | gamers = json }, Cmd.none )

        OpenGamers (Err e) ->
            ( Debug.log (toString e) model, Cmd.none )

        GetGamers ->
            ( model, getInfo model.page.title )

        UpdateGamers string ->
            ( { model | page = (updateSelection string) }, Cmd.none )


updateSelection : String -> Page
updateSelection string =
    Page string



--View


view : Model -> Html Msg
view model =
    div []
        [ div []
            [ input [ type_ "text", placeholder "Page", onInput UpdateGamers ] []
            , button [ onClick GetGamers ] [ text "Go!" ]
            , h3 [] [ text model.page.title ]
            , h3 [] [ text <| "http://localhost:4711/" ++ model.page.title ]
            , div [] <| List.map postView model.gamers
            ]
        ]


postView : Gamer -> Html Msg
postView post =
    div []
        [ a [ href post.score ] [ text post.nickname ]
        , div [][text "<<+++++++++++++++++++++++++++++++++++++++>>"]
        , div [] [ text "<<====================================================>>" ]
        ]



--Subscriptions


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



--Commands


getInfo : String -> Cmd Msg
getInfo string =
    let
        score =
            "http://localhost:4711/" ++ string

        req =
            Http.get score decodeReddit
    in
        Http.send OpenGamers req



--Json


decodeReddit : Json.Decoder (List Gamer)
decodeReddit =
    Json.at [ "data", "children" ] (Json.list decodeGamer)


decodeGamer : Json.Decoder Gamer
decodeGamer =
    Json.map4 Gamer
        (Json.at [ "data", "nickname" ] Json.string)
        (Json.at [ "data", "score" ] Json.string)
        (Json.at [ "data", "id" ] Json.string)


main =
    Html.program
        { init = init
        , update = update
        , view = view
        , subscriptions = subscriptions
        }