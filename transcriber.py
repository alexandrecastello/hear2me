from huggingsound import SpeechRecognitionModel
import joblib

def load_model():
    #Function that loads SpeechRecognition model from huggingface

    #load model
    model = SpeechRecognitionModel(
        "Edresson/wav2vec2-large-xlsr-coraa-portuguese")

    #save the model to disk
    #filename = 'model.joblib'
    #joblib.dump(model, filename)
    return model


def transcribe(audio):
    #Function that loads model and transcribe audio

    #model = joblib.load('model.joblib')
    model = load_model()

    transcription = model.transcribe([audio])

    text = transcription[0]['transcription']

    return text
