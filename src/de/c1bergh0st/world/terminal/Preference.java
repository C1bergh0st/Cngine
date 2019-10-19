package de.c1bergh0st.world.terminal;

public enum Preference {
    I_DISPLAY_RES_X(1920),
    I_DISPLAY_RES_Y(1080),
    D_DISPLAY_PRESCALER(1),
    D_DISPLAY_ZOOM(1);

    Preference(Object standard){
        defaultValue = standard.toString();
    }

    private String defaultValue;

    public String getDefaultValue(){
        return defaultValue;
    }
}
