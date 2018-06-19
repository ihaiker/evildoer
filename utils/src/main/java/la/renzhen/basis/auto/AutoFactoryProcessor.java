package la.renzhen.basis.auto;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.Cleanup;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @author zhouhaichao(a)2008.sina.com
 * @version 1.0 &amp; 0:03　2016/1/31
 */
@AutoService(Processor.class)
public class AutoFactoryProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<TypeMirror, List<Element>> factoryBeans = new HashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoFactory.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                if (!element.getModifiers().contains(Modifier.PUBLIC) || element.getModifiers().contains(Modifier.ABSTRACT)) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "the class mast be public and or abstract!", element);
                }
                AutoFactory autoFactory = element.getAnnotation(AutoFactory.class);
                TypeMirror inter = getFactoryInterface(element, autoFactory);
                if (inter == null) {
                    return true;
                }
                List<Element> elements = factoryBeans.get(inter);
                if (elements == null) {
                    elements = new ArrayList<>();
                }
                if (autoFactory.def()) {
                    elements.add(0, element);
                } else {
                    elements.add(element);
                }
                factoryBeans.put(inter, elements);
            }
        }
        for (Map.Entry<TypeMirror, List<Element>> entry : factoryBeans.entrySet()) {
            ClassName superInterface = ClassName.bestGuess(entry.getKey().toString());
            ClassName factoryName = ClassName.bestGuess(superInterface.simpleName() + "Factory");

            List<Element> elements = entry.getValue();


            MethodSpec.Builder method = MethodSpec.methodBuilder("get")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(factoryName, "worker")
                    .returns(superInterface);

            String statement = "switch(worker){ \n";
            for (Element element : elements) {
                String name = element.getSimpleName().toString().replace(superInterface.simpleName(), "");
                statement += " case " + name + " : return new " + element.getSimpleName() + "(); \n";
            }
            statement += "}";
            statement += " return new " + elements.get(0).getSimpleName() + "()";
            method.addStatement(statement);


            TypeSpec.Builder type = TypeSpec.enumBuilder(factoryName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(method.build());

            for (Element element : elements) {
                String name = element.getSimpleName().toString().replace(superInterface.simpleName(), "");
                type.addEnumConstant(name);
            }

            writeToSourceFile(JavaFile.builder(superInterface.packageName(), type.build()).build());
        }
        return true;
    }

    public void writeToSourceFile(JavaFile javaFile) {
        try {
            String packageName = javaFile.packageName;
            String className = javaFile.typeSpec.name;
            JavaFileObject jfo = this.processingEnv.getFiler().createSourceFile(packageName + "." + className);
            @Cleanup Writer writer = jfo.openWriter();
            javaFile.writeTo(writer);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), null);
        }
    }

    protected TypeMirror getFactoryInterface(Element element, AutoFactory autoFactory) {
        try {
            Class<?> clzz = autoFactory.value();
        } catch (MirroredTypesException e) {
            List<? extends TypeMirror> typeMirrors = e.getTypeMirrors();
            if (typeMirrors.get(0).toString().equals(Void.class.getName())) {
                TypeElement typeElement = (TypeElement) element;
                List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
                if (interfaces.size() == 1) {
                    return interfaces.get(0);
                } else {
                    List<TypeMirror> mirrors = new ArrayList<>(1);
                    for (TypeMirror typeMirror : interfaces) {
                        if (!typeMirror.toString().startsWith("java")) {
                            mirrors.add(typeMirror);
                        }
                    }
                    if (mirrors.size() == 1) {
                        return mirrors.get(0);
                    } else {
                        processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.ERROR, "Cannot determine the interface", element);
                    }
                    return null;
                }
            } else {
                return typeMirrors.get(0);
            }
        }
        return null;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(AutoFactory.class.getName());
    }
}
