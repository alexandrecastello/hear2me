from huggingsound import SpeechRecognitionModel
import joblib

def load_model():
    #Function that loads SpeechRecognition model from huggingface

    #load model
    model = SpeechRecognitionModel(
        "jonatasgrosman/wav2vec2-large-xlsr-53-portuguese")

    #save the model to disk
    filename = 'model_j.joblib'
    joblib.dump(model, filename)
    return model


def transcribe(audio):
    #Function that loads model and transcribe audio

    model = joblib.load('model.joblib')
    #model = load_model()

    transcription = model.transcribe([audio])

    text = transcription[0]['transcription'] + '.'

    return text
